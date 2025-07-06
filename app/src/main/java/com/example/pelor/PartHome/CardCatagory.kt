package com.example.pelor.PartHome

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
fun CardCatagory(
    title: String,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    val colorScheme = MaterialTheme.colorScheme

    val selectedColor = colorScheme.primary
    val unselectedBorderColor = colorScheme.outline.copy(alpha = 0.5f)
    val selectedTextColor = colorScheme.primary
    val unselectedTextColor = colorScheme.onSurface.copy(alpha = 0.7f)
    val containerColor = colorScheme.surface

    Card(
        modifier = Modifier
            .shadow(4.dp, shape = CircleShape, clip = false)
            .wrapContentSize()
            .clickable { onClick() },
        shape = CircleShape,
        border = BorderStroke(
            width = 1.dp,
            color = if (isSelected) selectedColor else unselectedBorderColor
        ),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 7.dp
                ),
                text = title,
                color = if (isSelected) selectedTextColor else unselectedTextColor,
                fontSize = 11.sp
            )
        }
    }
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