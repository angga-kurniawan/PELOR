package com.example.pelor.PartGelis

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.pelor.R

@Composable
fun CardGelis(
    status: String,
    name: String,
    codeGelis: String,
    pengalaman: String,
    onClick: () -> Unit,
    painter: AsyncImagePainter
) {
    Box(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .fillMaxWidth()
            .height(111.dp)
            .background(
                Color.White
            ),
        contentAlignment = Alignment.CenterStart,
        content = {
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Box(
                        modifier = Modifier.size(77.dp),
                        contentAlignment = Alignment.Center,
                        content = {
                            Image(
                                modifier = Modifier.fillMaxSize().clip(CircleShape),
                                painter = painter,
                                contentDescription = "",
                                contentScale = ContentScale.FillBounds
                            )
                            Box(
                                modifier = Modifier
                                    .padding(end = 5.dp, bottom = 5.dp)
                                    .clip(shape = CircleShape)
                                    .size(10.dp)
                                    .background(Color.Green)
                                    .align(Alignment.BottomEnd)
                            )
                        }
                    )
                    Column(
                        modifier = Modifier.padding(10.dp),
                        content = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                content = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        content = {
                                            Text(
                                                text = name,
                                                color = Color(0xFF323232),
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .padding(
                                                        start = 10.dp,
                                                        end = 10.dp
                                                    )
                                                    .clip(shape = CircleShape)
                                                    .size(5.dp)
                                                    .background(Color(0xFF323232))
                                            )
                                            Text(
                                                text = codeGelis,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    )
                                    Icon(
                                        modifier = Modifier.size(15.dp),
                                        painter = painterResource(R.drawable.morved),
                                        contentDescription = "",
                                        tint = Color(0xFF818181)
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.size(3.dp))
                            Text(
                                text = "Pengalaman: $pengalaman",
                                fontSize = 10.sp
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Text(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(color = Color(color = 0x5a00dd33))
                                    .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp),
                                text = status,
                                fontSize = 10.sp,
                                color = Color(0xFF3C8F16)
                            )
//                            Spacer(modifier = Modifier.size(7.dp))
//                            Text(
//                                text = "Kontak : $kontak",
//                                fontSize = 10.sp
//                            )
//                            Spacer(modifier = Modifier.size(3.dp))
//                            Text(
//                                text = "Pengalaman: $pengalaman",
//                                fontSize = 10.sp
//                            )
                        }
                    )
                }
            )
        }
    )
}

@Preview
@Composable
private fun CardGelisPrev() {
    CardGelis(
        name = "Guardian",
        status = "Tersedia",
        codeGelis = "001",
//        kontak = "08XXXXXXXX",
        pengalaman = "3 Tahun",
        onClick = {

        },
        painter = rememberAsyncImagePainter("")
    )
}