package com.example.pelor.Service

import com.google.firebase.Timestamp

data class UploadResponse(
    val label: String,
    val label_id: String,
    val confidence: Double,
    val threshold_check: String
)

data class Driver(
    val idDriver: String = "",
    val pengalaman: String = "",
    val status: Boolean = false
)

data class User(
    val uid: String = "",
    val username: String = "",
    val email: String = "",
    val profil: String = ""
)

data class DriverWithUser(
    val driver: Driver,
    val user: User
)

data class Message(
    val idPengirim: String = "",
    val message: String = "",
    val timestamp: Timestamp? = null
)

data class ChatPreview(
    val user: User,
    val chatId: String,
    val chatTerakhir: String,
    val idDriver: String? = null,
    val timestampTerakhir: Timestamp? = null
)

data class ImageLocation(
    val id: String = "",
    val imgUrl: String = ""
)