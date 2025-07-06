package com.example.pelor.PartAccount

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pelor.R


@Composable
fun CardSubMission(
    subTitle: String,
    painter: Int,
    isCompleted: Boolean = false,
    onClick: () -> Unit
) {
    val isDark = isSystemInDarkTheme()

    val cardColor = if (isCompleted) Color(0xFF888C94) else Color(0xFFA5A8B1)

    val backgroundColor = if (isDark) {
        MaterialTheme.colorScheme.background
    } else {
        Color(0xFFEBEBEB)
    }

    Box(
        modifier = Modifier
            .then(if (!isCompleted) Modifier.clickable { onClick() } else Modifier)
            .fillMaxWidth()
            .height(60.dp)
            .background(backgroundColor),
        contentAlignment = Alignment.CenterEnd
    ) {
        Log.d("MISI_STATUS", "subTitle: $subTitle | completed: $isCompleted")

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
                .background(cardColor),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 4.dp, top = 2.dp, bottom = 2.dp),
                    painter = painterResource(R.drawable.dotmission),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = subTitle,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.protestrevolutionregular))
                )
            }

            if (isCompleted) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selesai",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(26.dp)
                )
            } else {
                Icon(
                    modifier = Modifier
                        .size(33.dp)
                        .padding(end = 10.dp),
                    painter = painterResource(painter),
                    contentDescription = null,
                    tint = Color(0xFFD9D9D9)
                )
            }
        }
    }
}

@Preview
@Composable
private fun CardSubMissionPrev() {
    CardSubMission(
        subTitle = "Berkunjung ke masjid sultan riau",
        painter = R.drawable.teleportmisi,
        onClick = {}
    )
}