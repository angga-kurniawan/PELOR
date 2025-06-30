package com.example.pelor.PartPersonChat

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    onSend : () -> Unit
) {
    TextField(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color(0xFF368BF4),
                shape = RoundedCornerShape(5.dp)
            )
            .fillMaxWidth(),
        value = input,
        onValueChange = { onValueChange(it) },
        shape = RoundedCornerShape(5.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            IconButton(
                onClick = {
                    onSend()
                },
                content = {
                    Icon(
                        painter = painterResource(R.drawable.outline_send_24),
                        contentDescription = null,
                    )
                }
            )
        },
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun TextFieldInputChatPersonPrev() {
    var input = remember { mutableStateOf("") }
    TextFieldInputChatPerson(input = input.value, onValueChange = { input.value = it }, onSend = {})
}