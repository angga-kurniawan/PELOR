package com.example.pelor.PopUpScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pelor.R

@Composable
fun PopUpTheme(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color(0xFFB9B9B9),
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .width(250.dp)
            .wrapContentHeight()
            .padding(20.dp),
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Box(
                        modifier = Modifier
                            .border(width = 1.dp, color = Color(0xffc8c8c8), shape = CircleShape)
                            .clip(CircleShape)
                            .size(60.dp)
                            .background(Color(0xffefefef)),
                        content = {

                        }
                    )
                    IconButton(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(30.dp),
                        onClick = {
                            onClick()
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color(0xFFD9D9D9)
                        ),
                        content = {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(R.drawable.baseline_close_24),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    )
                }
            )
            Spacer(modifier = Modifier.padding(10.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.padding(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Box(
                        modifier = Modifier
                            .clickable {  }
                            .border(
                                width = 1.dp,
                                color = Color(0xffc8c8c8),
                                shape = CircleShape
                            )
                            .clip(CircleShape)
                            .size(60.dp)
                            .background(Color(0xffefefef)),
                        content = {

                        }
                    )
                    Box(
                        modifier = Modifier
                            .clickable { }
                            .border(width = 1.dp, color = Color(0xffc8c8c8), shape = CircleShape)
                            .clip(CircleShape)
                            .size(60.dp)
                            .background(Color(0xff4c4c4c)),
                        content = {

                        }
                    )
                    Box(
                        modifier = Modifier
                            .clickable { }
                            .border(width = 1.dp, color = Color(0xffc8c8c8), shape = CircleShape)
                            .clip(CircleShape)
                            .size(60.dp)
                            .background(Color(0xc7ff6200)),
                        content = {

                        }
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PopUpThemePrev() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = {
            PopUpTheme(
                onClick = {}
            )
        }
    )
}