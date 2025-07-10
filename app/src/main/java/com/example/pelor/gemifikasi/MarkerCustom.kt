package com.example.pelor.gemifikasi

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.pelor.R
import com.example.pelor.Service.EventAgendaData
import com.example.pelor.Service.KategoriData
import com.example.pelor.Service.MarkerItem
import com.example.pelor.Service.ZoneData
import org.osmdroid.util.GeoPoint

@Composable
fun getKategoriMarkers(): Map<KategoriType, KategoriData> {

    return mapOf(
        KategoriType.SEJARAH to KategoriData.MarkerList(
            listOf(
                MarkerItem(
                    titleResId = R.string.masjid_raya_sultan_riau,
                    latitude = 0.929495,
                    longitude = 104.420336,
                    iconResId = R.drawable.masjidrayasultanriau
                ),
                MarkerItem(
                    titleResId = R.string.gedung_tabib,
                    latitude = 0.928505,
                    longitude = 104.421165,
                    iconResId = R.drawable.markergedungtabib
                ),
                MarkerItem(
                    titleResId = R.string.makam_raja_ali_haji,
                    latitude = 0.927650,
                    longitude = 104.421420,
                    iconResId = R.drawable.markermakamalihaji
                ),
                MarkerItem(
                    titleResId = R.string.balai_adat_melayu,
                    latitude = 0.927301,
                    longitude = 104.414701,
                    iconResId = R.drawable.markerbalaiadatmelayu
                ),
                MarkerItem(
                    titleResId = R.string.rumah_hakim,
                    latitude = 0.924828,
                    longitude = 104.419829,
                    iconResId = R.drawable.markergedunghakim
                ),
                MarkerItem(
                    titleResId = R.string.benteng_bukit_kursi,
                    latitude = 0.929350,
                    longitude = 104.417748,
                    iconResId = R.drawable.bentengbukitkursi
                ),
                MarkerItem(
                    titleResId = R.string.makam_engku_putri,
                    latitude = 0.927596,
                    longitude = 104.421363,
                    iconResId = R.drawable.markermakamengkuputri
                )
            )
        ),

        KategoriType.EVENT_AGENDA to KategoriData.EventAgenda(
            EventAgendaData(
                zones = listOf(
                    ZoneData(
                        points = listOf(
                            GeoPoint(0.930055, 104.417319),
                            GeoPoint(0.929964, 104.417957),
                            GeoPoint(0.929822, 104.417936),
                            GeoPoint(0.929717, 104.418593),
                            GeoPoint(0.929535, 104.418654),
                            GeoPoint(0.928883, 104.417600),
                            GeoPoint(0.930055, 104.417319)
                        ),
                        fillColor = Color(0xff66ffd500),
                        strokeColor = Color.Yellow
                    ),
                    ZoneData(
                        points = listOf(
                            GeoPoint(0.929717, 104.418593),
                            GeoPoint(0.929535, 104.418654),
                            GeoPoint(0.929441, 104.418831),
                            GeoPoint(0.929463, 104.419100),
                            GeoPoint(0.929371, 104.419430),
                            GeoPoint(0.929911, 104.419749),
                            GeoPoint(0.930098, 104.419714),
                            GeoPoint(0.930144, 104.419368),
                            GeoPoint(0.930361, 104.419330),
                            GeoPoint(0.930434, 104.419127),
                            GeoPoint(0.930246, 104.419089),
                            GeoPoint(0.930114, 104.419279),
                            GeoPoint(0.929943, 104.419328),
                            GeoPoint(0.929924, 104.418888),
                            GeoPoint(0.929597, 104.418933),
                            GeoPoint(0.929699, 104.418810)
                        ),
                        fillColor = Color(0x66ff0000),
                        strokeColor = Color.Red
                    )
                ),
                marker = MarkerItem(
                    titleResId = R.string.pasar_tradisional,
                    latitude = 0.929382,
                    longitude = 104.417721,
                    iconResId = R.drawable.markermakamalihaji
                )
            )
        ),

        KategoriType.KULINER to KategoriData.EventAgenda(
            EventAgendaData(
                zones = listOf(
                    ZoneData(
                        points = listOf(
                            GeoPoint(0.929602, 104.420704),
                            GeoPoint(0.929626, 104.420765),
                            GeoPoint(0.929565, 104.420800),
                            GeoPoint(0.929999, 104.421323),
                            GeoPoint(0.929776, 104.421452),
                            GeoPoint(0.929288, 104.420720),

                            ),
                        fillColor = Color(0xff665eff00),
                        strokeColor = Color.Green
                    )
                ),
                marker = null
            )
        )
    )
}

enum class KategoriType(@StringRes val labelResId: Int) {
    SEJARAH(R.string.kategori_sejarah),
    EVENT_AGENDA(R.string.kategori_event_agenda),
    KULINER(R.string.kategori_kuliner_melayu)
}

enum class TitleType(@StringRes val labelResId: Int) {
    BALAI_ADAT(R.string.title_balai_adat),
    BENTENG_KURSI(R.string.title_benteng_kursi),
    GEDUNG_TABIB(R.string.title_gedung_tabib),
    MAKAM_ENGKU_PUTRI(R.string.title_makam_engku_putri),
    MAKAM_RAJA_ALI_HAJI(R.string.title_makam_raja_ali_haji),
    MASJID_RAYA(R.string.title_masjid_raya),
    RUMAH_HAKIM(R.string.title_rumah_hakim)
}