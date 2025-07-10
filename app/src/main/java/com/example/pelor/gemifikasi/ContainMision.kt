package com.example.pelor.gemifikasi

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.pelor.R

//data class Kegiatan(
//    val judul: String,
//    val aktivitas: Map<String,String>,
//    @DrawableRes val icon: Int
//)
//
//@Composable
//fun getDaftarKegiatan(): List<Kegiatan> {
//    return listOf(
//        Kegiatan(
//            judul = stringResource(R.string.kegiatan_judul_lagenda),
//            aktivitas = mapOf(
//                stringResource(R.string.aktivitas_masjid) to stringResource(R.string.deskripsi_masjid),
//                stringResource(R.string.aktivitas_balai) to stringResource(R.string.deskripsi_balai),
//                stringResource(R.string.aktivitas_makam) to stringResource(R.string.deskripsi_makam),
//            ),
//            icon = R.drawable.topengmisi
//        ),
//        Kegiatan(
//            judul = stringResource(R.string.kegiatan_judul_penjelajah),
//            aktivitas = mapOf(
//                stringResource(R.string.aktivitas_foto_sejarah) to stringResource(R.string.deskripsi_foto_sejarah)
//            ),
//            icon = R.drawable.iconprimarymisi2
//        )
//    )
//}

data class Aktivitas(
    val id: String,
    val titleRes: Int,
    val descRes: Int
)

data class Kegiatan(
    val judulRes: Int,
    val aktivitas: List<Aktivitas>,
    @DrawableRes val icon: Int
)

@Composable
fun getDaftarKegiatan(): List<Kegiatan> {
    return listOf(
        Kegiatan(
            judulRes = R.string.kegiatan_judul_lagenda,
            aktivitas = listOf(
                Aktivitas(
                    id = "Berkunjung ke Masjid Sultan Riau",
                    titleRes = R.string.aktivitas_masjid,
                    descRes = R.string.deskripsi_masjid
                ),
                Aktivitas(
                    id = "Berkunjung ke Balai Adat Melayu",
                    titleRes = R.string.aktivitas_balai,
                    descRes = R.string.deskripsi_balai
                ),
                Aktivitas(
                    id = "Berkunjung ke Makam Raja Ali Haji",
                    titleRes = R.string.aktivitas_makam,
                    descRes = R.string.deskripsi_makam
                )
            ),
            icon = R.drawable.topengmisi
        ),
        Kegiatan(
            judulRes = R.string.kegiatan_judul_penjelajah,
            aktivitas = listOf(
                Aktivitas(
                    id = "Foto dengan Bangunan Bersejarah",
                    titleRes = R.string.aktivitas_foto_sejarah,
                    descRes = R.string.deskripsi_foto_sejarah
                )
            ),
            icon = R.drawable.iconprimarymisi2
        )
    )
}
