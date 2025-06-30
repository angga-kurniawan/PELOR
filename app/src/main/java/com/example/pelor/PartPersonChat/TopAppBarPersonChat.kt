package com.example.pelor.PartPersonChat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.pelor.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarPersonChat(
    navController: NavController?,
    imgUrl: String,
    userName: String,
    code: String
) {
    TopAppBar(
        modifier = Modifier
            .shadow(elevation = 1.dp, shape = RoundedCornerShape(bottomEnd = 5.dp, bottomStart = 5.dp))
            .clip(
                RoundedCornerShape(
                    bottomStart = 5.dp,
                    bottomEnd = 5.dp
                )
            )
            .drawBehind {
                val strokeWidth = 2.dp.toPx()
                val y = size.height - strokeWidth / 2
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Box(
                        modifier = Modifier.size(40.dp).offset(y = 2.dp),
                        content = {
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                model = imgUrl,
                                contentDescription = "",
                                contentScale = ContentScale.Fit,
                            )
                            Box(
                                modifier = Modifier
                                    .padding(end = 5.dp, bottom = 5.dp)
                                    .clip(shape = CircleShape)
                                    .size(8.dp)
                                    .background(Color.Green)
                                    .align(Alignment.BottomEnd)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.padding(7.dp))
                    SecNameAndCode(
                        userName = userName,
                        code = code,
                        textColor = Color(0xFF323232)
                    )
                }
            )
        },
        navigationIcon = {
            Icon(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .size(25.dp)
                    .clickable {
                        navController?.popBackStack()
                    },
                painter = painterResource(R.drawable.baseline_arrow_back_24),
                contentDescription = null,
                tint = Color(0xFF878787)
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun TopAppBarPersonChatPrev() {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBarPersonChat(navController = rememberNavController(), userName = "", imgUrl = "", code = "")
    }
}