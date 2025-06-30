package com.example.pelor.gemifikasi

import com.example.pelor.R

data class MarkerItem(
    val title: String,
    val latitude: Double,
    val longitude: Double,
    val iconResId: Int
)

val kategoriMarkers = mapOf(
    "Sejarah" to listOf(
        MarkerItem(
            title = "Masjid Raya Sultan Riau",
            latitude = 0.929495,
            longitude = 104.420336,
            iconResId = R.drawable.masjidrayasultanriau
        ),
        MarkerItem(
            title = "Gedung Tabib",
            latitude = 0.928505,
            longitude = 104.421165,
            iconResId = R.drawable.markergedungtabib
        ),
        MarkerItem(
            title = "Makam Raja Ali Haji",
            latitude = 0.927650,
            longitude = 104.421420,
            iconResId = R.drawable.markermakamalihaji
        ),
        MarkerItem(
            title = "Balai Adat Melayu",
            latitude = 0.927301,
            longitude = 104.414701,
            iconResId = R.drawable.markerbalaiadatmelayu
        ),
        MarkerItem(
            title = "Rumah Hakim",
            latitude = 0.924828,
            longitude = 104.419829,
            iconResId = R.drawable.markergedunghakim
        ),
        MarkerItem(
            title = "Benteng Bukit Kursi",
            latitude = 0.929350,
            longitude = 104.417748,
            iconResId = R.drawable.bentengbukitkursi
        ),
        MarkerItem(
            title = "Makam Engku Putri",
            latitude = 0.927596,
            longitude = 104.421363,
            iconResId = R.drawable.markermakamengkuputri
        )
    ),
    "Penginapan" to listOf(
        MarkerItem(
            title = "Nabila Homestay",
            latitude = 0.9287101749875097,
            longitude = 104.42105098075625,
            iconResId = R.drawable.markernabilahomestay
        ),MarkerItem(
            title = "Penginapan Sulthan Pulau Penyengat",
            latitude = 0.9284278904364163 ,
            longitude = 104.42053679642885,
            iconResId = R.drawable.markersultanhomestay
        ),MarkerItem(
            title = "Aisyah homestay penyengat",
            latitude = 0.9282226651563578,
            longitude = 104.42328261198928,
            iconResId = R.drawable.markeraisyahhomstay
        ),
    ),
    "Event & Agenda" to listOf(

    ),
    "Kuliner" to listOf(

    ),
    "Restourant" to listOf(

    )
)