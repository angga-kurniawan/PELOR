package com.example.pelor.gemifikasi

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.pelor.R

data class Kegiatan(
    val judul: String,
    val aktivitas: Map<String,String>,
    @DrawableRes val icon: Int
)

@Composable
fun getDaftarKegiatan(): List<Kegiatan> {
    return listOf(
        Kegiatan(
            judul = stringResource(R.string.kegiatan_judul_lagenda),
            aktivitas = mapOf(
                stringResource(R.string.aktivitas_masjid) to stringResource(R.string.deskripsi_masjid),
                stringResource(R.string.aktivitas_balai) to stringResource(R.string.deskripsi_balai),
                stringResource(R.string.aktivitas_makam) to stringResource(R.string.deskripsi_makam),
            ),
            icon = R.drawable.topengmisi
        ),
        Kegiatan(
            judul = stringResource(R.string.kegiatan_judul_penjelajah),
            aktivitas = mapOf(
                stringResource(R.string.aktivitas_foto_sejarah) to stringResource(R.string.deskripsi_foto_sejarah)
            ),
            icon = R.drawable.iconprimarymisi2
        )
    )
}

