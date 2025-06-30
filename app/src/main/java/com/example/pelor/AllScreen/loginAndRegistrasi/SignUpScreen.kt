package com.example.pelor.AllScreen.loginAndRegistrasi

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pelor.Service.AuthLoginAndRegistrasi.register
import com.example.pelor.PartSignIn.CustomTextField
import com.example.pelor.R

@Composable
fun SignUpScreen(navController: NavController) {
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    fun validateName(value: String) {
        userName = value
        nameError = if (value.isBlank()) "Full Name is required" else null
    }

    fun validateEmail(value: String) {
        email = value
        emailError = when {
            value.isBlank() -> "Email is required"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches() -> "Invalid email format"
            else -> null
        }
    }

    fun validatePassword(value: String) {
        password = value
        passwordError = when {
            value.isBlank() -> "Password is required"
            value.length < 8 -> "Password must be at least 8 characters"
            else -> null
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = {
            Icon(
                modifier = Modifier
                    .padding(top = 70.dp)
                    .align(Alignment.TopCenter)
                    .size(width = 162.23.dp, height = 143.26.dp),
                painter = painterResource(R.drawable.logopelor),
                contentDescription = "",
                tint = Color(0xFF368BF4)
            )

            Image(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(height = 363.dp),
                painter = painterResource(R.drawable.wave),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )

            Box(
                contentAlignment = Alignment.TopCenter,
                content = {
                    Image(
                        modifier = Modifier
                            .width(width = 336.dp)
                            .height(height = 430.dp),
                        painter = painterResource(R.drawable.bgsignup),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds
                    )
                    Column(
                        modifier = Modifier
                            .padding(top = 50.dp)
                            .width(270.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = {
                            CustomTextField(
                                title = "Username",
                                value = userName,
                                placeholder = "Your Name",
                                isError = nameError != null,
                                onValueChange = { userName -> validateName(userName) }
                            )
                            nameError?.let {
                                Text(
                                    text = it,
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            CustomTextField(
                                title = "Email",
                                value = email,
                                placeholder = "Exempel@gmail.com",
                                isError = emailError != null,
                                onValueChange = { validateEmail(it) }
                            )
                            emailError?.let {
                                Text(
                                    text = it,
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            CustomTextField(
                                title = "Password",
                                value = password,
                                placeholder = "Password",
                                isError = passwordError != null,
                                visualTransformation = PasswordVisualTransformation(),
                                trailingIcon = {
                                    val image = if (passwordVisible)
                                        R.drawable.outline_visibility_24
                                    else R.drawable.outline_visibility_off_24
                                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                        Icon(
                                            modifier = Modifier
                                                .size(20.dp),
                                            painter = painterResource(image),
                                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                            tint = Color(0xFF368BF4)
                                        )
                                    }
                                },
                                onValueChange = { validatePassword(it) }
                            )
                            passwordError?.let {
                                Text(
                                    text = it,
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(30.dp))
                            Box(
                                modifier = Modifier
                                    .clickable {
                                        validateName(userName)
                                        validateEmail(email)
                                        validatePassword(password)
                                        if (nameError == null && emailError == null && passwordError == null) {
                                            isLoading = true
                                            register(
                                                username = userName,
                                                email = email,
                                                password = password,
                                                onSuccess = {
                                                    Log.d("RegisterDebug", "Success callback jalan")
                                                    isLoading = false
                                                    navController.popBackStack()
                                                },
                                                onError = { error ->
                                                    Log.e("RegisterDebug", "Error callback jalan: $error")
                                                    isLoading = false
                                                    emailError = error
                                                }
                                            )
                                        }
                                    }
                                    .clip(shape = RoundedCornerShape(5.dp))
                                    .background(
                                        color = Color(0xFF368BF4)
                                    )
                                    .width(width = 280.dp)
                                    .height(height = 48.dp),
                                contentAlignment = Alignment.Center,
                                content = {
                                    if (isLoading) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(24.dp),
                                            color = Color.White
                                        )
                                    } else {
                                        Text(
                                            text = "REGISTRASI",
                                            color = Color.White,
                                            textAlign = TextAlign.Center,
                                            fontFamily = FontFamily(Font(R.font.poppinsreguler))
                                        )
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            Row(
                                content = {
                                    Text(
                                        text = "have an account? ",
                                        color = Color(0x54000000),
                                        fontSize = 10.sp,
                                        fontFamily = FontFamily(Font(R.font.poppinsreguler))
                                    )
                                    Text(
                                        text = "Sign In",
                                        color = Color(0xA81200B9),
                                        fontSize = 10.sp,
                                        fontFamily = FontFamily(Font(R.font.poppinsreguler))
                                    )
                                }
                            )
                        }
                    )
                }
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .align(Alignment.BottomCenter),
                text = "DewiPupe ©2025",
                fontSize = 8.sp,
                color = Color(0xFFFFFFFF),
                fontFamily = FontFamily(Font(R.font.poppinsreguler))
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPrev() {
    SignUpScreen(rememberNavController())
}