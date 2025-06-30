package com.example.pelor.PartAccount

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pelor.R
import com.example.pelor.gemifikasi.daftarKegiatan

@Composable
fun SecMission(
    misiSelesaiList: List<String>,
    onClick: (String, String) -> Unit
) {
    var expandedItems = remember { mutableStateMapOf<Int, Boolean>() }

    Column {
        daftarKegiatan.forEachIndexed { index, kegiatan ->
            val isExpanded = expandedItems[index] ?: true

            CardPrimaryMission(
                title = kegiatan.judul,
                painter = kegiatan.icon,
                onClick = {
                    expandedItems[index] = !isExpanded
                }
            )

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                    kegiatan.aktivitas.forEach { aktifitas ->
                        val isSelesai = aktifitas.key in misiSelesaiList
                        Log.e("penentu misi","${aktifitas.key} $misiSelesaiList")
                        CardSubMission(
                            subTitle = aktifitas.key,
                            painter = R.drawable.teleportmisi,
                            isCompleted = isSelesai,
                            onClick = {
                                onClick(aktifitas.key, aktifitas.value)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SecMissionPrev() {
    SecMission(onClick = { s: String, s2: String ->  }, misiSelesaiList = listOf())
}

