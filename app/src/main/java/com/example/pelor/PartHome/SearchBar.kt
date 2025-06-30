package com.example.pelor.PartHome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pelor.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarCustom(
    xp: Int,
    maxXp: Int,
    query: String,
    onQueryChange: (String) -> Unit,
    imgUrl : String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 15.dp)
            .shadow(4.dp, shape = CircleShape, clip = false)
            .clip(CircleShape)
            .height(54.dp)
            .fillMaxWidth()
            .background(Color.White)
            .border(width = 1.dp, color = Color(0xFFF8F5F5), shape = CircleShape),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Box(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray),
                content = {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                onClick()
                            },
//                        painter = painterResource(R.drawable.profile),
                        contentDescription = "",
                        model = imgUrl
                    )
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxSize(),
                        progress = xp.toFloat() / maxXp.toFloat(),
                        color = Color(0xFF368BF4),
                        strokeWidth = 3.dp
                    )
                }
            )
            TextField(
                value = query,
                onValueChange = onQueryChange,
                placeholder = {
                    Text(
                        text = "Search...",
                        color = Color(0xFFD0D0D0)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                maxLines = 1,
                singleLine = true
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun TopAppBarCustomPrev() {
    var searchQuery by remember { mutableStateOf("") }
    TopAppBarCustom(
        xp = 6435,
        maxXp = 10000,
        query = searchQuery,
        onQueryChange = { searchQuery = it },
        onClick = {},
        imgUrl = ""
    )
}