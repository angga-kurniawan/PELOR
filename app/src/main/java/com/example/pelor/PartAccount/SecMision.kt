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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pelor.R
import com.example.pelor.gemifikasi.getDaftarKegiatan

@Composable
fun SecMission(
    misiSelesaiList: List<String>,
    onClick: (String, String, String) -> Unit
) {
    var expandedItems = remember { mutableStateMapOf<Int, Boolean>() }
    val kegiatan = getDaftarKegiatan()

    Column {
        kegiatan.forEachIndexed { index, kegiatan ->
            val isExpanded = expandedItems[index] ?: true

            CardPrimaryMission(
                title = stringResource(kegiatan.judulRes),
                painter = kegiatan.icon,
                onClick = {
                    expandedItems[index] = !isExpanded
                }
            )

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                    kegiatan.aktivitas.forEach { aktifitas ->
                        val isSelesai =
                            misiSelesaiList.any { it.equals(aktifitas.id, ignoreCase = true) }
                        val desc = stringResource(aktifitas.descRes)
                        val title = stringResource(aktifitas.titleRes)
                        Log.e("penentu misi", "${aktifitas.id} $misiSelesaiList")
                        CardSubMission(
                            subTitle = stringResource(aktifitas.titleRes),
                            painter = R.drawable.teleportmisi,
                            isCompleted = isSelesai,
                            onClick = {
                                onClick(title, desc, aktifitas.id)
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
    SecMission(onClick = { s: String, s2: String, s3 : String -> }, misiSelesaiList = listOf())
}

