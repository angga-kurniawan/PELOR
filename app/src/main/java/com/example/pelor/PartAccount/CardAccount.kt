package com.example.pelor.PartAccount

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pelor.R

@Composable
fun CardAccount(
    name: String,
    title: String,
    imgUrl: String,
    ar: Int,
    xp: Int,
    maxXp: Int,
    onClick: () -> Unit,
    onClickEdit: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(188.dp)
            .background(Color.DarkGray),
        content = {
            Image(
                painter = painterResource(R.drawable.bg_baal_with_shadow),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
            Row(
                modifier = Modifier.padding(top = 20.dp, start = 10.dp),
                content = {
                    Column(
                        content = {
                            Icon(
                                modifier = Modifier.clickable {
                                    onClick()
                                },
                                painter = painterResource(R.drawable.baseline_arrow_back_24),
                                contentDescription = "",
                                tint = Color.White
                            )
                            Box(
                                contentAlignment = Alignment.Center,
                                content = {
                                    Image(
                                        modifier = Modifier.size(120.dp),
                                        painter = painterResource(R.drawable.bgprofile),
                                        contentDescription = null
                                    )
                                    AsyncImage(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .border(2.dp, Color.White, CircleShape)
                                            .size(100.dp),
                                        contentDescription = null,
                                        model = imgUrl
                                    )
                                }
                            )
                        }
                    )
                    Column(
                        content = {
                            Box(
                                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                                content = {
                                    Image(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(70.dp),
                                        painter = painterResource(R.drawable.bg_shadow_small),
                                        contentDescription = "",
                                        contentScale = ContentScale.FillBounds
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        content = {
                                            Column(
                                                modifier = Modifier.padding(
                                                    top = 5.dp,
                                                    start = 10.dp,
                                                    end = 10.dp,
                                                    bottom = 10.dp
                                                ),
                                                content = {
                                                    Text(
                                                        text = name,
                                                        color = Color.White,
                                                        fontSize = 15.sp
                                                    )
                                                    Text(
                                                        modifier = Modifier.padding(top = 7.dp),
                                                        text = title,
                                                        color = Color.Cyan,
                                                        fontSize = 10.sp
                                                    )
                                                }
                                            )
                                            Image(
                                                modifier = Modifier
                                                    .size(30.dp)
                                                    .padding(end = 10.dp)
                                                    .clickable {
                                                        onClickEdit()
                                                    },
                                                painter = painterResource(R.drawable.edit_name_account),
                                                contentDescription = ""
                                            )
                                        }
                                    )
                                }
                            )
                            Box(
                                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 7.dp),
                                content = {
                                    Image(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(80.dp),
                                        painter = painterResource(R.drawable.bg_shadow_small),
                                        contentDescription = "",
                                        contentScale = ContentScale.FillBounds
                                    )
                                    Column(
                                        modifier = Modifier.padding(10.dp),
                                        content = {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically,
                                                content = {
                                                    Text(
                                                        text = stringResource(R.string.adventure_rank),
                                                        color = Color.White,
                                                        fontSize = 14.sp
                                                    )
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        content = {
                                                            Text(
                                                                modifier = Modifier.padding(end = 10.dp),
                                                                text = ar.toString(),
                                                                color = Color.White
                                                            )
                                                            Icon(
                                                                modifier = Modifier
                                                                    .size(19.dp)
                                                                    .clickable {

                                                                    },
                                                                painter = painterResource(R.drawable.baseline_info_24),
                                                                contentDescription = "",
                                                                tint = Color.White
                                                            )
                                                        }
                                                    )
                                                }
                                            )
                                            Spacer(modifier = Modifier.height(10.dp))
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.End,
                                                content = {
                                                    Text(
                                                        text = "$xp / $maxXp",
                                                        color = Color.White,
                                                        fontSize = 10.sp
                                                    )
                                                }
                                            )
                                            Spacer(modifier = Modifier.height(5.dp))
                                            Row(
                                                content = {
                                                    Box(
                                                        modifier = Modifier
                                                            .height(8.dp)
                                                            .fillMaxWidth()
                                                            .background(
                                                                Color.Transparent,
                                                                shape = RoundedCornerShape(4.dp)
                                                            )
                                                            .border(
                                                                1.dp,
                                                                Color.White,
                                                                shape = RoundedCornerShape(4.dp)
                                                            ),
                                                        content = {
                                                            LinearProgressIndicator(
                                                                progress = xp.toFloat() / maxXp.toFloat(),
                                                                strokeCap = StrokeCap.Round,
                                                                color = Color(0xFF00A2FF),
                                                                trackColor = Color.Transparent,
                                                                modifier = Modifier
                                                                    .height(8.dp)
                                                                    .fillMaxWidth()
                                                                    .clip(RoundedCornerShape(4.dp))
                                                            )
                                                        }
                                                    )
                                                }
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun CardAccountPrev() {
    CardAccount(
        name = "Ussui",
        title = "Petualng Bersejarah",
        ar = 25,
        imgUrl = "",
        xp = 6568,
        maxXp = 10000,
        onClick = {

        },
        onClickEdit = {

        }
    )
}