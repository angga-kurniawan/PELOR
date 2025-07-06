package com.example.pelor.PopUpScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pelor.R

@Composable
fun PopUpSucsses(status: Boolean,keterangan: String = "") {
    Box(
        modifier = Modifier
            .border(width = 1.dp, color = Color(0xFFB9B9B9), shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .width(250.dp)
            .height(200.dp)
            .background(color = Color.White),
        contentAlignment = Alignment.Center,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Icon(
                        modifier = Modifier.size(70.dp),
                        painter = painterResource(if (status) R.drawable.iconsuccses else R.drawable.iconfailed),
                        contentDescription = null,
                        tint = if (status) Color(0xff72e102) else Color.Red
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = if (status) stringResource(R.string.popup_success_title) else stringResource(R.string.popup_failed_title),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.poppinsreguler)),
                        color = Color(0xff7c7c7c)
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PopUpSucssesPrev() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = {
            PopUpSucsses(
                status = false
            )
        }
    )
}