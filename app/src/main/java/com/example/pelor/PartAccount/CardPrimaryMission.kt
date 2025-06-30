package com.example.pelor.PartAccount

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pelor.R

@Composable
fun CardPrimaryMission(title: String, painter: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = Color(0xFF313C56))
            .clickable { onClick() },
        contentAlignment = Alignment.CenterEnd,
        content = {
            Icon(
                modifier = Modifier.size(100.dp),
                painter = painterResource(painter),
                contentDescription = "",
                tint = Color(0xFF8D8D8D)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .border(color = Color(0xFF67663C), width = 1.dp),
                contentAlignment = Alignment.CenterStart,
                content = {
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = title,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.protestrevolutionregular))
                    )
                }
            )
        }
    )
}

@Preview
@Composable
private fun CardPrimaryMissionPrev() {
    CardPrimaryMission(
        title = "LAGENDA PULAU PENYENGAT",
        painter = R.drawable.topengmisi,
        onClick = {}
    )
}