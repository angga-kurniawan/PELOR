package com.example.pelor.PartSignIn

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pelor.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    title: String,
    placeholder: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean,
    onValueChange: (String) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val borderColor = if (isError) colors.error else Color(0xFF368BF4)

    Box(
        contentAlignment = Alignment.CenterStart
    ) {
        TextField(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(5.dp)
                )
                .fillMaxWidth(),
            isError = isError,
            value = value,
            onValueChange = { onValueChange(it) },
            shape = RoundedCornerShape(5.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface
            ),
            placeholder = {
                Text(
                    text = placeholder
                )
            },
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon,
            singleLine = true
        )

        CompCustomTextField(
            title = title,
            modifier = Modifier.offset(x = 10.dp, y = (-25).dp),
            backgroundColor = colors.background,
            textColor = borderColor
        )
    }
}

@Composable
fun CompCustomTextField(
    title: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    textColor: Color
) {
    Text(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(5.dp)
            )
            .border(
                width = 1.dp,
                color = textColor,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(horizontal = 10.dp, vertical = 5.dp),
        text = title,
        color = textColor,
        style = MaterialTheme.typography.bodySmall
    )
}

@Preview(showBackground = true)
@Composable
private fun CompCustomTextFieldPrev() {
//    CompCustomTextField(title = "email")
}

@Preview(showBackground = false)
@Composable
private fun CustomTextFieldPrev() {
    CustomTextField(
        value = "",
        title = "Password",
        placeholder = "Password",
        visualTransformation = PasswordVisualTransformation(),
        isError = true,
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .clickable {

                    }
                    .size(20.dp),
                painter = painterResource(R.drawable.outline_visibility_off_24),
                contentDescription = "",
                tint = Color(0xFF368BF4)
            )
        },
        onValueChange = {

        }
    )
}