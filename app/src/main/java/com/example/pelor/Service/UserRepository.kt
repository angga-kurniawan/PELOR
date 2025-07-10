package com.example.pelor.Service

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ProfileRepository {
    @SuppressLint("StaticFieldLeak")
    val db = FirebaseFirestore.getInstance()
    val profilMap = mutableMapOf<String, String>()
    val titleMap = mutableMapOf<String, String>()

    fun fetchProfiles(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        db.collection("profil")
            .get()
            .addOnSuccessListener { result ->
                profilMap.clear()
                for (doc in result) {
                    val level = doc.getString("level")
                    val image = doc.getString("image url")
                    if (!level.isNullOrEmpty() && !image.isNullOrEmpty()) {
                        profilMap[level] = image
                    }
                }
                Log.d("ProfileRepository", "Isi profilMap: $profilMap")
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onError(exception.message ?: "Error fetching profile")
            }
    }

    fun fetchTitle(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        db.collection("title")
            .get()
            .addOnSuccessListener { result ->
                titleMap.clear()
                for (doc in result) {
                    val level = doc.getString("level")
                    val gelar = doc.getString("gelar")
                    if (!level.isNullOrEmpty() && !gelar.isNullOrEmpty()) {
                        titleMap[level] = gelar
                    }
                }
                Log.d("ProfileRepository", "Isi profilMap: $titleMap")
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onError(exception.message ?: "Error fetching profile")
            }
    }

    fun fetchDriver(
        onSuccess: (List<DriverWithUser>) -> Unit,
        onError: (String) -> Unit,
        currentUserId: String? = null
    ) {
        val resultList = mutableListOf<DriverWithUser>()
        db.collection("driver")
            .get()
            .addOnSuccessListener { driverDocs ->
                if (driverDocs.isEmpty) {
                    onError("Tidak ada data driver")
                    return@addOnSuccessListener
                }

                val tasks = mutableListOf<Task<*>>()

                for (doc in driverDocs) {
                    val idDriver = doc.getString("idDriver") ?: continue
                    val pengalaman = doc.getString("pengalaman") ?: ""
                    val status = doc.getBoolean("status") ?: false
                    val driver = Driver(idDriver, pengalaman, status)

                    val userRef = doc.getDocumentReference("users") ?: continue
                    val task = userRef.get().addOnSuccessListener { userDoc ->
                        val username = userDoc.getString("username") ?: ""
                        val uid = userDoc.getString("uid") ?: ""
                        val email = userDoc.getString("email") ?: ""
                        val profil = userDoc.getString("profil") ?: ""
                        val user =
                            User(username = username, email = email, profil = profil, uid = uid)

                        if (uid.isNotBlank() && uid != currentUserId) {
                            resultList.add(DriverWithUser(driver, user))
                        } else {
                            Log.d("FILTER_UID", "Skipped UID: $uid | Current: $currentUserId")
                        }
                    }

                    tasks.add(task)
                }

                Tasks.whenAllComplete(tasks)
                    .addOnSuccessListener {
                        onSuccess(resultList)
                    }
                    .addOnFailureListener {
                        onError(it.message ?: "Gagal mengambil data user")
                    }
            }
            .addOnFailureListener {
                onError(it.message ?: "Gagal mengambil data driver")
            }
    }

    fun createOrGetChat(
        currentUserId: String,
        driverUserId: String,
        onSuccess: (chatId: String) -> Unit,
        onError: (String) -> Unit
    ) {
        val currentUserRef = db.collection("users").document(currentUserId)
        val driverRef = db.collection("users").document(driverUserId)

        db.collection("chat")
            .whereArrayContains("partisipasi", currentUserRef)
            .get()
            .addOnSuccessListener { snapshot ->
                val existingChat = snapshot.documents.firstOrNull { doc ->
                    val participants = doc.get("partisipasi") as? List<*>
                    participants?.any { it is DocumentReference && it.path == driverRef.path } == true
                }

                if (existingChat != null) {
                    onSuccess(existingChat.id)
                } else {
                    val newChat = mapOf(
                        "partisipasi" to listOf(currentUserRef, driverRef),
                        "chatTerakhir" to ""
                    )
                    db.collection("chat")
                        .add(newChat)
                        .addOnSuccessListener { doc ->
                            onSuccess(doc.id)
                        }
                        .addOnFailureListener {
                            onError("Gagal membuat chat: ${it.message}")
                        }
                }
            }
            .addOnFailureListener {
                onError("Gagal cek chat: ${it.message}")
            }
    }

    fun fetchChatParticipants(
        currentUserId: String,
        onSuccess: (List<ChatPreview>) -> Unit,
        onError: (String) -> Unit
    ) {
        val currentUserRef = db.collection("users").document(currentUserId)

        db.collection("driver").get()
            .addOnSuccessListener { driverSnapshot ->
                val driverMap = driverSnapshot.documents.associateBy {
                    it.getDocumentReference("users")?.path
                }

                db.collection("chat")
                    .whereArrayContains("partisipasi", currentUserRef)
                    .get()
                    .addOnSuccessListener { chatSnapshot ->
                        val chatPreviews = mutableListOf<ChatPreview>()
                        val tasks = mutableListOf<Task<DocumentSnapshot>>()

                        for (chatDoc in chatSnapshot.documents) {
                            val participants =
                                chatDoc.get("partisipasi") as? List<DocumentReference> ?: continue
                            val lawanRef =
                                participants.firstOrNull { it.path != currentUserRef.path }
                                    ?: continue
                            val chatTerakhir = chatDoc.getString("chatTerakhir") ?: ""
                            val chatId = chatDoc.id

                            val task = lawanRef.get().addOnSuccessListener { userDoc ->
                                val uid = userDoc.getString("uid") ?: return@addOnSuccessListener
                                val username = userDoc.getString("username") ?: ""
                                val email = userDoc.getString("email") ?: ""
                                val profil = userDoc.getString("profil") ?: ""
                                val timestampTerakhir = chatDoc.getTimestamp("timestampTerakhir")
                                val user = User(uid, username, email, profil)

                                val driverDoc = driverMap[lawanRef.path]
                                val idDriver = driverDoc?.getString("idDriver")

                                chatPreviews.add(
                                    ChatPreview(
                                        user = user,
                                        chatId = chatId,
                                        chatTerakhir = chatTerakhir,
                                        idDriver = idDriver,
                                        timestampTerakhir = timestampTerakhir
                                    )
                                )
                            }

                            tasks.add(task)
                        }

                        Tasks.whenAllComplete(tasks)
                            .addOnSuccessListener {
                                onSuccess(chatPreviews)
                            }
                            .addOnFailureListener {
                                onError(it.message ?: "Gagal mengambil data user")
                            }
                    }
                    .addOnFailureListener {
                        onError(it.message ?: "Gagal mengambil data chat")
                    }
            }
            .addOnFailureListener {
                onError(it.message ?: "Gagal mengambil data driver")
            }
    }

    fun listenToMessages(
        chatId: String,
        currentUserId: String,
        onUserLoaded: (user: User?, driverCode: String?) -> Unit = { _, _ -> },
        onUpdate: (List<Message>) -> Unit
    ) {
        val currentUserRef = db.collection("users").document(currentUserId)

        db.collection("chat").document(chatId).get()
            .addOnSuccessListener { chatDoc ->
                val partisipasi = chatDoc.get("partisipasi") as? List<DocumentReference>
                    ?: return@addOnSuccessListener
                val lawanRef = partisipasi.firstOrNull { it.path != currentUserRef.path }
                    ?: return@addOnSuccessListener

                lawanRef.get().addOnSuccessListener { userDoc ->
                    val uid = userDoc.getString("uid") ?: return@addOnSuccessListener
                    val username = userDoc.getString("username") ?: ""
                    val email = userDoc.getString("email") ?: ""
                    val profil = userDoc.getString("profil") ?: ""
                    val user = User(uid, username, email, profil)

                    db.collection("driver")
                        .whereEqualTo("users", lawanRef)
                        .get()
                        .addOnSuccessListener { driverSnapshot ->
                            val driverCode = driverSnapshot.documents.firstOrNull()
                                ?.getString("idDriver")
                            onUserLoaded(user, driverCode)
                        }
                        .addOnFailureListener {
                            onUserLoaded(user, null)
                        }
                }
            }

        db.collection("chat")
            .document(chatId)
            .collection("isi chat")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("🔥 listenToMessages", "Error: ${e.message}")
                    return@addSnapshotListener
                }

                val messages = snapshot?.documents?.mapNotNull {
                    try {
                        it.toObject(Message::class.java)
                    } catch (ex: Exception) {
                        Log.e("🔥 listenToMessages", "Gagal parsing message: ${ex.message}")
                        null
                    }
                } ?: emptyList()

                Log.d("🔥 listenToMessages", "Jumlah pesan: ${messages.size}")
                onUpdate(messages)
            }
    }

    fun sendMessage(chatId: String, message: Message, onSuccess: () -> Unit = {}) {
        val db = FirebaseFirestore.getInstance()
        val messageMap = mapOf(
            "idPengirim" to message.idPengirim,
            "message" to message.message,
            "timestamp" to message.timestamp
        )

        db.collection("chat")
            .document(chatId)
            .collection("isi chat")
            .add(messageMap)
            .addOnSuccessListener {
                db.collection("chat")
                    .document(chatId)
                    .update(
                        mapOf(
                            "chatTerakhir" to message.message,
                            "timestampTerakhir" to message.timestamp
                        )
                    )
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("SEND_MESSAGE", "Gagal kirim pesan: ${e.message}")
            }
    }

    fun fetchImages(
        collection: String,
        onSuccess: (List<ImageLocation>) -> Unit,
        onError: (String) -> Unit
    ) {
        val mappedCollection = when (collection) {
            "malay traditional hall" -> "balai adat melayu"
            "kursi hill fort" -> "benteng bukit kursi"
            "physicians building" -> "gedung tabib"
            "engku putris tomb" -> "makam engku putri"
            "raja ali hajis tomb" -> "makam raja ali haji"
            "sultan riau grand mosque" -> "masjid raya sultan riau"
            "judges house" -> "rumah hakim"
            else -> collection.lowercase()
        }
        Log.e("map pade collection",mappedCollection)
        db.collection(mappedCollection)
            .get()
            .addOnSuccessListener { snapshot ->
                val list = snapshot.documents.mapNotNull { doc ->
                    val imgUrl = doc.getString("imgUrl") ?: return@mapNotNull null
                    ImageLocation(id = doc.id, imgUrl = imgUrl)
                }
                onSuccess(list)
            }
            .addOnFailureListener {
                onError(it.message ?: "Gagal mengambil gambar")
            }
    }

    fun addXpToUser(
        amount: Int,
        onSuccess: (Boolean) -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val userRef = db.collection("users").document(uid)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(userRef)
            val currentXp = snapshot.getLong("xp") ?: 0L
            val currentLevel = snapshot.getLong("level") ?: 1L

            val newTotalXp = currentXp + amount
            val levelIncrement = newTotalXp / 10000
            val sisaXp = newTotalXp % 10000
            val newLevel = currentLevel + levelIncrement

            val isLevelUp = levelIncrement > 0

            Log.d("XP_DEBUG", "XP: $currentXp + $amount = $newTotalXp, Naik Level: $levelIncrement, Sisa: $sisaXp, NewLevel: $newLevel")

            transaction.update(userRef, mapOf(
                "xp" to sisaXp,
                "level" to newLevel
            ))

            isLevelUp
        }.addOnSuccessListener { isLevelUp ->
            Log.d("XP_RESULT", "✅ Update Berhasil. LevelUp? $isLevelUp")
            onSuccess(isLevelUp)
        }.addOnFailureListener {
            onError(it.message ?: "Gagal update XP & level")
        }
    }

}


class UploadRepository {

    suspend fun uploadImage(context: Context, uri: Uri): UploadResponse? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val tempFile = File.createTempFile("upload_", ".jpg", context.cacheDir)
            tempFile.outputStream().use { inputStream.copyTo(it) }

            val requestFile = tempFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", tempFile.name, requestFile)

            val response = ApiClient.apiService.uploadImage(body)
            Log.e("API RESULT2", response.body().toString())
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun uploadToCloudinary(context: Context, uri: Uri): String? {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = context.contentResolver.openInputStream(uri) ?: return@withContext null
                val file = File.createTempFile("upload", ".jpg", context.cacheDir)
                file.outputStream().use { inputStream.copyTo(it) }

                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val multipart = MultipartBody.Part.createFormData("file", file.name, requestFile)

                val uploadPreset = "unsigned_present" // Ganti ini dengan nama upload preset kamu di Cloudinary
                val uploadPresetBody = uploadPreset.toRequestBody("text/plain".toMediaTypeOrNull())

                val response = CloudinaryApiClient.apiService.uploadToCloudinary(multipart, uploadPresetBody)
                if (response.isSuccessful) {
                    response.body()?.secure_url
                } else {
                    Log.e("CLOUDINARY_ERROR", "Gagal: ${response.errorBody()?.string()}")
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}

fun formatChatTimestamp(timestamp: Timestamp?): String {
    if (timestamp == null) return "-"
    val messageTime = timestamp.toDate()
    val now = Date()

    val diff = now.time - messageTime.time
    val minute = 60 * 1000
    val hour = 60 * minute
    val day = 24 * hour

    val dateFormatToday = SimpleDateFormat("HH:mm", Locale.getDefault())
    val dateFormatFull = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))
    val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale("id", "ID"))

    return when {
        diff < minute -> "Baru saja"
        diff < hour * 12 -> dateFormatToday.format(messageTime)
        diff < day * 2 -> "Kemarin"
        diff < day * 7 -> dayOfWeekFormat.format(messageTime)
        else -> dateFormatFull.format(messageTime)
    }
}


