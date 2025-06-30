package com.example.pelor.Service

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore

object AuthLoginAndRegistrasi {
    val auth = FirebaseAuth.getInstance()
    @SuppressLint("StaticFieldLeak")
    val db = FirebaseFirestore.getInstance()

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Login", "Login berhasil")
                    onSuccess()
                } else {
                    val errorMessage = when (val exception = task.exception) {
                        is FirebaseAuthInvalidCredentialsException -> "Password salah. Silakan coba lagi."
                        is FirebaseAuthInvalidUserException -> "Email tidak terdaftar."
                        else -> exception?.localizedMessage ?: "Login gagal. Silakan coba lagi nanti."
                    }
                    Log.e("LoginError", errorMessage)
                    onError(errorMessage)
                }
            }
    }

    fun register(
        username: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val uid = authResult.user?.uid
                if (uid == null) {
                    onError("UID tidak ditemukan.")
                    return@addOnSuccessListener
                }

                val userMap = mapOf(
                    "uid" to uid,
                    "username" to username,
                    "email" to email,
                    "password" to password,
                    "level" to 1,
                    "profil" to "https://res.cloudinary.com/drwwnzu1r/image/upload/v1750698734/gddsd4lbb8i9tngusdoa.png",
                    "xp" to 0,
                    "title" to "Pengembara Pemula",
                    "pengguna" to "pengembara"
                )

                db.collection("users")
                    .document(uid)
                    .set(userMap)
                    .addOnSuccessListener {
                        Log.d("Register", "Registrasi berhasil dan data disimpan.")
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        Log.e("FirestoreError", "Gagal simpan data: ${e.message}")
                        onError("Registrasi berhasil, tapi gagal menyimpan data.")
                    }
            }
            .addOnFailureListener { exception ->
                val errorMessage = when (exception) {
                    is FirebaseAuthUserCollisionException -> "Email sudah digunakan."
                    is FirebaseAuthWeakPasswordException -> "Password terlalu lemah (minimal 6 karakter)."
                    is FirebaseAuthInvalidCredentialsException -> "Format email tidak valid."
                    else -> exception.localizedMessage ?: "Registrasi gagal. Coba lagi nanti."
                }
                Log.e("RegisterError", errorMessage, exception)
                onError(errorMessage)
            }
    }

    fun logout(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            auth.signOut()
            onSuccess()
        } catch (e: Exception) {
            Log.e("LogoutError", "Gagal logout", e)
            onError("Terjadi kesalahan saat logout.")
        }
    }
}

