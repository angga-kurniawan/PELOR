package com.example.pelor.PartHome

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CardCatagory(title: String, onClick: () -> Unit, isSelected: Boolean) {
    Card(
        modifier = Modifier
            .shadow(4.dp, shape = CircleShape, clip = false)
            .wrapContentSize()
            .clickable { onClick() },
        shape = CircleShape,
        border = BorderStroke(
            1.dp,
            color = if (isSelected) Color(0xFF2196F3) else Color(0xFFDBDBDB)
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        content = {
            Box(
                contentAlignment = Alignment.Center,
                content = {
                    Text(
                        modifier = Modifier.padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = 7.dp,
                            bottom = 7.dp
                        ),
                        text = title,
                        color = if (isSelected) Color(0xFF2196F3) else Color(0xFF5D5D5D),
                        fontSize = 11.sp
                    )
                }
            )
        }
    )
}


@Preview
@Composable
private fun CardCatagoryPrev() {
    CardCatagory(
        title = "sejarah",
        onClick = {  },
        isSelected = true
    )
}