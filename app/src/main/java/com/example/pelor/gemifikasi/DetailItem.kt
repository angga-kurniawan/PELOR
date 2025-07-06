package com.example.pelor.gemifikasi

import android.content.Context
import com.example.pelor.R

data class SejarahItem(
    val title: String,
    val sejarah: String
)

data class KategoriDetail(
    val category: String,
    val items: List<SejarahItem>
)

fun getKategoriDetails(context: Context): List<KategoriDetail> {
    return listOf(
        KategoriDetail(
            category = context.getString(R.string.kategori_sejarah),
            items = listOf(
                SejarahItem(
                    title = context.getString(R.string.title_balai_adat),
                    sejarah = context.getString(R.string.sejarah_balai_adat)
                ),
                SejarahItem(
                    title = context.getString(R.string.title_benteng_kursi),
                    sejarah = context.getString(R.string.sejarah_benteng_kursi)
                ),
                SejarahItem(
                    title = context.getString(R.string.title_gedung_tabib),
                    sejarah = context.getString(R.string.sejarah_gedung_tabib)
                ),
                SejarahItem(
                    title = context.getString(R.string.title_makam_engku_putri),
                    sejarah = context.getString(R.string.sejarah_makam_engku_putri)
                ),
                SejarahItem(
                    title = context.getString(R.string.title_makam_raja_ali_haji),
                    sejarah = context.getString(R.string.sejarah_makam_raja_ali_haji)
                ),
                SejarahItem(
                    title = context.getString(R.string.title_masjid_raya),
                    sejarah = context.getString(R.string.sejarah_masjid_raya)
                ),
                SejarahItem(
                    title = context.getString(R.string.title_rumah_hakim),
                    sejarah = context.getString(R.string.sejarah_rumah_hakim)
                )
            )
        ),
        KategoriDetail(
            category = context.getString(R.string.kategori_penginapan),
            items = listOf(
                SejarahItem(
                    title = context.getString(R.string.title_gedung_tabib),
                    sejarah = context.getString(R.string.sejarah_gedung_tabib)
                ),
                SejarahItem(
                    title = context.getString(R.string.title_rumah_hakim),
                    sejarah = context.getString(R.string.sejarah_rumah_hakim)
                )
            )
        ),
        KategoriDetail(
            category = context.getString(R.string.kategori_event_agenda),
            items = listOf(
                SejarahItem(
                    title = context.getString(R.string.title_makam_raja_ali_haji),
                    sejarah = context.getString(R.string.sejarah_makam_raja_ali_haji)
                ),
                SejarahItem(
                    title = context.getString(R.string.title_makam_engku_putri),
                    sejarah = context.getString(R.string.sejarah_makam_engku_putri)
                )
            )
        ),
        KategoriDetail(
            category = context.getString(R.string.kategori_restoran),
            items = listOf(
                SejarahItem(
                    title = context.getString(R.string.title_balai_adat),
                    sejarah = context.getString(R.string.sejarah_balai_adat)
                )
            )
        )
    )
}