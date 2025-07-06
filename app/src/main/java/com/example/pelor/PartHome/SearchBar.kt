package com.example.pelor.PartHome

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun TopAppBarCustom(
    xp: Int,
    maxXp: Int,
    query: String,
    onQueryChange: (String) -> Unit,
    imgUrl: String,
    onClick: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 15.dp)
            .shadow(4.dp, shape = CircleShape, clip = false)
            .clip(CircleShape)
            .height(54.dp)
            .fillMaxWidth()
            .background(colorScheme.surface)
            .border(width = 1.dp, color = colorScheme.outline.copy(alpha = 0.2f), shape = CircleShape),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(start = 10.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(colorScheme.onSurface.copy(alpha = 0.2f))
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onClick() },
                contentDescription = null,
                model = imgUrl
            )
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = xp.toFloat() / maxXp.toFloat(),
                color = colorScheme.primary,
                strokeWidth = 3.dp
            )
        }

        TextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = {
                Text(
                    text = "Search...",
                    color = colorScheme.onSurface.copy(alpha = 0.4f)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = colorScheme.primary,
                unfocusedTextColor = colorScheme.onSurface,
                focusedTextColor = colorScheme.onSurface
            ),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )
    }
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