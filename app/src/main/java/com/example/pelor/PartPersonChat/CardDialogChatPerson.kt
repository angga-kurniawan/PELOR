package com.example.pelor.PartPersonChat

import android.text.style.AlignmentSpan
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pelor.R

@Composable
fun CardDialogCharPerson(
    input: String = "text",
    time: String = "20:00",
    person: Boolean = true
) {
    val colorScheme = MaterialTheme.colorScheme

    val backgroundBubble = if (person) {
        colorScheme.primary.copy(alpha = 0.14f)
    } else {
        colorScheme.primary.copy(alpha = 0.05f)
    }

    val textColor = colorScheme.onSurface
    val timeColor = colorScheme.onSurface.copy(alpha = 0.6f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = if (person) 50.dp else 0.dp, end = if (person) 0.dp else 50.dp),
        contentAlignment = if (person) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Column(
            horizontalAlignment = if (person) Alignment.End else Alignment.Start
        ) {
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(backgroundBubble)
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                fontFamily = FontFamily(Font(R.font.poppinsreguler)),
                color = textColor,
                text = input
            )
            Text(
                modifier = Modifier.padding(start = 5.dp, top = 5.dp),
                text = time,
                fontSize = 10.sp,
                color = timeColor,
                fontFamily = FontFamily(Font(R.font.poppinsreguler))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CardDialogChatPersonPrev() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        content = {
            CardDialogCharPerson(
                input = "Pariwisata Budaya Pulau Penyengat 2025\n" +
                        "\n" +
                        "Pulau Penyengat, wisata budaya di Kepulauan Riau, terus menarik perhatian wisatawan lokal maupun mancanegara. Pada 03 Mei 2025, diselenggarakan rangkaian kegiatan edukatif bertajuk Jejak Aksi yang mengangkat sejarah dan kearifan lokal Pulau Penyengat.\n" +
                        "Kegiatan ini mencakup tur keliling situs bersejarah seperti Masjid Raya Sultan Riau, kompleks makam raja-raja, dan benteng pertahanan peninggalan Kesultanan Riau-Lingga. Wisatawan juga diajak mengikuti sesi pengenalan aksara Jawi serta kuliner tradisional khas Melayu.\n" +
                        "Melalui pendekatan wisata edukatif ini, Pulau Penyengat tidak hanya menjadi destinasi liburan, tapi juga ruang belajar hidup tentang budaya, sejarah, dan identitas Melayu. Program ini diharapkan dapat memperkuat minat wisata budaya dan mendorong pelestarian warisan lokal secara berkelanjutan.",
                person = true
            )
            Spacer(modifier = Modifier.padding(10.dp))
            CardDialogCharPerson(
                input = "ok pak",
                person = false
            )
        }
    )
}