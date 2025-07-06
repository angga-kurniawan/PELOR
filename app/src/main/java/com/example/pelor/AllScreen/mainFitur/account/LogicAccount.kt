package com.example.pelor.AllScreen.mainFitur.account

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object LogicAccount {
    fun getUserData(
        onSuccess: (UserData) -> Unit,
        onError: (String) -> Unit,
        uid: String?
    ) {

        if (uid == null) {
            onError("User belum login")
            return
        }

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val username = document.getString("username") ?: "Unknown"
                    val title = document.getString("title") ?: "No Title"
                    val level = document.getLong("level")?.toInt() ?: 0
                    val xp = document.getLong("xp")?.toInt() ?: 0
                    val profil = document.getString("profil") ?: ""

                    onSuccess(UserData(username, title, level, xp, profil))
                } else {
                    onError("Data user tidak ditemukan")
                }
            }
            .addOnFailureListener { e ->
                onError("Gagal ambil data: ${e.message}")
            }
    }
    fun updateUserProfile(
        userId: String,
        username: String,
        profil: String,
        title: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val data = mapOf(
            "username" to username,
            "profil" to profil,
            "title" to title
        )

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .update(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it.message ?: "Gagal memperbarui profil") }
    }
}



data class UserData(
    val username: String,
    val title: String,
    val level: Int,
    val xp: Int,
    val profil: String
)