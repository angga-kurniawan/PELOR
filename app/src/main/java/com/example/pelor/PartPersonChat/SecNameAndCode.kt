package com.example.pelor.PartPersonChat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
fun SecNameAndCode(
    userName: String = "UserName",
    code: String = "001",
    textColor: Color = Color.Black
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Text(
                text = userName,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.poppinsreguler)),
                color = textColor
            )
            Spacer(modifier = Modifier.padding(3.dp))
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(3.dp)
                    .background(Color.Black),
                content = { }
            )
            Spacer(modifier = Modifier.padding(3.dp))
            Text(
                color = textColor,
                fontSize = 12.sp,
                text = code,
                fontFamily = FontFamily(Font(R.font.poppinsreguler))
            )
        }
    )
}

@Preview
@Composable
private fun SecNameAndCodePrev() {
    SecNameAndCode()
}