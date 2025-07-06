package com.example.pelor.Service

import androidx.compose.ui.graphics.Color
import com.google.firebase.Timestamp
import org.osmdroid.util.GeoPoint

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
    val profil: String = "",
    val xp: Int = 0,
    val level: Int = 1
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

data class MarkerItem(
    val title: String,
    val latitude: Double,
    val longitude: Double,
    val iconResId: Int
)

data class ZoneData(
    val points: List<GeoPoint>,
    val fillColor: Color,
    val strokeColor: Color
)

data class EventAgendaData(
    val zones: List<ZoneData>,
    val marker: MarkerItem? = null
)

sealed class KategoriData {
    data class MarkerList(val markers: List<MarkerItem>) : KategoriData()
    data class EventAgenda(val data: EventAgendaData) : KategoriData()
}