package com.example.pelor.PartChat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.pelor.R
import com.google.firebase.Timestamp

@Composable
fun CardPersonCard(
    onClick: () -> Unit,
    name: String,
    chat: String,
    painter: AsyncImagePainter,
    code: String,
    time: String
) {
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .height(75.dp)
            .background(colorScheme.surface),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(55.dp)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
                Box(
                    modifier = Modifier
                        .padding(end = 3.dp, bottom = 3.dp)
                        .clip(CircleShape)
                        .size(10.dp)
                        .background(Color.Green)
                        .align(Alignment.BottomEnd)
                )
            }
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = name,
                            color = colorScheme.onSurface,
                            fontFamily = FontFamily(Font(R.font.poppinsreguler))
                        )
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .clip(CircleShape)
                                .size(5.dp)
                                .background(colorScheme.onSurface.copy(alpha = 0.5f))
                        )
                        Text(
                            text = code,
                            color = colorScheme.onSurface,
                            fontFamily = FontFamily(Font(R.font.poppinsreguler))
                        )
                    }
                    Text(
                        text = time,
                        fontSize = 12.sp,
                        color = colorScheme.onSurface.copy(alpha = 0.6f),
                        fontFamily = FontFamily(Font(R.font.poppinsreguler))
                    )
                }
                Spacer(modifier = Modifier.size(3.dp))
                Text(
                    text = chat,
                    color = colorScheme.onSurfaceVariant,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.poppinsreguler))
                )
            }
        }
    }
    HorizontalDivider(color = colorScheme.outline.copy(alpha = 0.2f))
}

@Preview
@Composable
private fun CardPersonCardPrev() {
    CardPersonCard({}, "", "", rememberAsyncImagePainter(""),"000", time = "00:01")
}