package com.example.pelor.Service

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class ApiViewModel : ViewModel() {

    private val repo = UploadRepository()

    var uploadResult by mutableStateOf<String?>(null)
        private set

    var uploadLabel by mutableStateOf<String?>(null)
        private set

    var uploadConfidence by mutableStateOf<Double?>(null)
        private set

    var showLevelUpDialog by mutableStateOf(false)
        private set

    var hasUploaded by mutableStateOf(false)
        private set

    fun setUploaded() {
        hasUploaded = true
    }

    fun resetUploadFlag() {
        hasUploaded = false
    }

    fun dismissLevelUpDialog() {
        showLevelUpDialog = false
    }

    fun uploadMission(
        context: Context,
        uri: Uri,
        expectedLabel: String,
        onResultReady: suspend (Boolean) -> Unit = {}
    ) {
        viewModelScope.launch {
            uploadLabel = null
            uploadResult = "Uploading..."
            val result = repo.uploadImage(context, uri)

            val confidence = result?.confidence ?: 0.0
            val labelRaw = result?.label?.trim().orEmpty()
            val resultLower = labelRaw.lowercase()
            val expectedLower = expectedLabel.lowercase().trim()

            val targetLower = when (expectedLower) {
                "berkunjung ke masjid sultan riau" -> "masjid raya sultan riau"
                "berkunjung ke makam raja ali haji" -> "makam raja ali haji"
                "berkunjung ke balai adat melayu" -> "balai adat melayu"
                else -> expectedLower
            }

            uploadConfidence = confidence
            uploadLabel = if (confidence >= 60.0) labelRaw else null

            Log.d("API RESULT", "Label: $labelRaw | Confidence: $confidence")
            Log.d(
                "API TARGET",
                "Expected: $expectedLabel | MatchTarget: $targetLower | ResultAPI: $resultLower"
            )

            if (confidence < 60.0 || !resultLower.contains(targetLower)) {
                uploadResult = "❌ Misi gagal: tidak cocok atau confidence rendah"
                Log.d("XP", "❌ Misi gagal: tidak cocok atau confidence rendah")
                delay(5000)
                onResultReady(false)
                return@launch
            }

            val userRef = FirebaseFirestore.getInstance()
                .collection("users")
                .document(FirebaseAuth.getInstance().currentUser?.uid ?: return@launch)

            FirebaseFirestore.getInstance().runTransaction { transaction ->
                val snapshot = transaction.get(userRef)
                val currentDone =
                    (snapshot.get("mission done") as? List<*>)?.filterIsInstance<String>()
                        ?: emptyList()
                val updatedDone = currentDone.toMutableSet().apply {
                    add(expectedLabel)
                }
                transaction.update(
                    userRef, mapOf(
                        "mission done" to updatedDone.toList()
                    )
                )
            }.addOnSuccessListener {
                ProfileRepository.addXpToUser(
                    amount = 7500,
                    onSuccess = { isLevelUp ->
                        Log.d("XP", "✅ XP Misi +7500 dan misi ditambahkan ke Firestore")
                        viewModelScope.launch {
                            uploadResult = result?.threshold_check
                            delay(5000)
                            if (isLevelUp) {
                                showLevelUpDialog = true
                            }
                            onResultReady(true)
                        }
                    },
                    onError = {
                        Log.e("XP", "❌ Gagal update XP: $it")
                        viewModelScope.launch {
                            onResultReady(false)
                        }
                    }
                )
            }.addOnFailureListener {
                viewModelScope.launch {
                    uploadResult = result?.threshold_check
                    Log.e("XP", "❌ Gagal update misi: ${it.message}")
                    delay(5000)
                    onResultReady(false)
                }
            }
        }
    }

    fun uploadImage(
        context: Context,
        uri: Uri,
        onResultReady: suspend (Boolean) -> Unit = {}
    ) {
        viewModelScope.launch {
            uploadLabel = null
            uploadResult = "Uploading..."
            val result = repo.uploadImage(context, uri)

            val confidence = result?.confidence ?: 0.0

            uploadConfidence = confidence
            uploadLabel = if (confidence >= 60.0) result?.label else null

            Log.e("API RESULT", "Confidence: $confidence")

            if (confidence >= 60.0) {
                ProfileRepository.addXpToUser(
                    amount = 2500,
                    onSuccess = { isLevelUp ->
                        Log.d("XP", "+2500 XP berhasil ditambahkan")
                        viewModelScope.launch {
                            uploadResult = result?.threshold_check
                            if (isLevelUp) {
                                showLevelUpDialog = true
                            }
                            onResultReady(true)
                        }
                    },
                    onError = {
                        Log.e("XP", "Gagal menambahkan XP: $it")
                        viewModelScope.launch {
                            onResultReady(false)
                        }
                    }
                )
            } else {
                uploadResult = result?.threshold_check ?: "Upload failed"
                onResultReady(false)
            }
        }
    }

    fun uploadDetectedImagesToFirestore(
        context: Context,
        uris: List<Uri>,
        locationName: String,
        loadingState: MutableState<Boolean>,
        loadingText: MutableState<String>,
        acceptedUris: SnapshotStateList<Uri>,
        rejectedUris: SnapshotStateList<Uri>,
        onUploadDone: () -> Unit
    ) {

        viewModelScope.launch {
            loadingState.value = true
            loadingText.value = "🔍 Mengecek ${uris.size} gambar..."
            uploadResult = loadingText.value

            acceptedUris.clear()
            rejectedUris.clear()

            val expectedLower = locationName.lowercase().trim()
            var success = 0
            var fail = 0

            for ((index, uri) in uris.withIndex()) {
                loadingText.value = "📷 Mengecek gambar ${index + 1}..."
                uploadResult = loadingText.value

                val result = repo.uploadImage(context, uri)
                val label = result?.label?.trim().orEmpty().lowercase()
                val confidence = result?.confidence ?: 0.0

                if (confidence >= 60.0 && label.contains(expectedLower)) {
                    loadingText.value = "📤 Mengupload gambar ${index + 1} ke Cloudinary..."
                    uploadResult = loadingText.value

                    val url = repo.uploadToCloudinary(context, uri)

                    if (url != null) {
                        try {
                            FirebaseFirestore.getInstance()
                                .collection(expectedLower)
                                .add(hashMapOf("imgUrl" to url))
                                .await()
                            acceptedUris.add(uri)
                            success++
                        } catch (e: Exception) {
                            rejectedUris.add(uri)
                            fail++
                        }
                    } else {
                        rejectedUris.add(uri)
                        fail++
                    }
                } else {
                    rejectedUris.add(uri)
                    fail++
                }
            }

            val finalResult = when {
                success == 0 -> "❌ Semua gambar tidak cocok atau gagal upload ($fail)"
                fail == 0 -> "✅ Semua gambar cocok dan berhasil disimpan ($success)"
                else -> "⚠️ $success gambar disimpan, $fail gagal atau tidak cocok"
            }

            loadingText.value = finalResult
            uploadResult = finalResult

            onUploadDone()
        }
    }
}