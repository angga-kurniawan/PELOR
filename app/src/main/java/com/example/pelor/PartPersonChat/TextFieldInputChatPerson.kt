package com.example.pelor.PartPersonChat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pelor.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldInputChatPerson(
    input: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSend: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Row (
        verticalAlignment = Alignment.CenterVertically
    ){
        TextField(
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = colorScheme.primary,
                    shape = RoundedCornerShape(5.dp)
                )
                .weight(1f),
            value = input,
            onValueChange = { onValueChange(it) },
            shape = RoundedCornerShape(5.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorScheme.surface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text("Type your message...")
            },
            singleLine = true
        )
        IconButton(
            onClick = onSend,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = colorScheme.primary
            )
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(R.drawable.outline_send_24),
                contentDescription = null,
                tint = colorScheme.onPrimary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldInputChatPersonPrev() {
    var input = remember { mutableStateOf("") }
    TextFieldInputChatPerson(input = input.value, onValueChange = { input.value = it }, onSend = {})
}