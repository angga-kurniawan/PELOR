package com.example.pelor.PartAccount

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
fun alertDialogExit(
    onClickYes: () -> Unit,
    onClickNo: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFB9B9B9)
            )
            .padding(20.dp),
        content = {
            Column(
                content = {
                    Row(
                        content = {
                            Text(
                                text = "Kamu Yakin Mau ",
                                fontFamily = FontFamily(Font(R.font.poppinsreguler)),
                                fontSize = 17.sp
                            )
                            Text(
                                text = "LogOut ",
                                fontFamily = FontFamily(Font(R.font.poppinsreguler)),
                                fontSize = 17.sp,
                                color = Color.Red
                            )
                            Text(
                                text = "?",
                                fontFamily = FontFamily(Font(R.font.poppinsreguler)),
                                fontSize = 17.sp
                            )
                        }
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Row(
                        modifier = Modifier.width(210.dp),
                        horizontalArrangement = Arrangement.End,
                        content = {
                            TextButton(
                                content = {
                                    Text(
                                        text = "Ya",
                                        fontFamily = FontFamily(Font(R.font.poppinsreguler)),
                                        color = Color(0xFF368BF4)
                                    )
                                },
                                onClick = { onClickYes() }
                            )
                            Button(
                                onClick = { onClickNo() },
                                content = {
                                    Text(
                                        text = "Tidak",
                                        fontFamily = FontFamily(Font(R.font.poppinsreguler)),
                                    )
                                },
                                shape = RoundedCornerShape(5.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF368BF4)
                                )
                            )
                        }
                    )
                }
            )
        }
    )
}

@Preview
@Composable
private fun AlertDialogPrev() {
    alertDialogExit(
        {}, {}
    )
}