package com.example.pelor.gemifikasi

import androidx.annotation.DrawableRes
import com.example.pelor.R

data class Kegiatan(
    val judul: String,
    val aktivitas: Map<String,String>,
    @DrawableRes val icon: Int
)

val daftarKegiatan = listOf(
    Kegiatan(
        judul = "LAGENDA PULAU PENYENGAT",
        aktivitas = mapOf(
            "Berkunjung ke Masjid Sultan Riau" to "Kamu Harus Menuju Ke Masjid Sultan RIau dan foto di sana untuk bisa mendapatkan XP",
            "Berkunjung ke Istana Kantor" to "Kamu Harus Menuju ke Istana Kantor dan foto di sana untuk bisa mendapatkan XP",
            "Berkunjung ke Makam Raja Ali Haji" to "Kamu Harus Menuju Ke Makam Raja Ali Haji dan foto di sana untuk bisa mendapatkan XP",
        ),
        icon =  R.drawable.topengmisi
    ),
    Kegiatan(
        judul = "PENJELAJAH MEDIA",
        aktivitas = mapOf(
             "Unggah 4 foto tempat bersejarah dari Pulau Penyengat" to "Unggah 4 foto tempat bersejarah dari Pulau Penyengat"
        ),
        icon = R.drawable.iconprimarymisi2
    )
)

