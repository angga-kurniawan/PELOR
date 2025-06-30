package com.example.pelor.AllScreen.loginAndRegistrasi

import android.content.ContentValues.TAG
import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
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
import com.example.pelor.Service.AuthLoginAndRegistrasi.login
import com.example.pelor.AllScreen.mainFitur.MainActivity
import com.example.pelor.PartSignIn.CustomTextField
import com.example.pelor.R

@Composable
fun SignInScreen(onBack: () -> Unit, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val createContext = LocalContext.current

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
                            .height(height = 372.dp),
                        painter = painterResource(R.drawable.bgloginandregister),
                        contentDescription = "",
                    )
                    Column(
                        modifier = Modifier
                            .padding(top = 50.dp)
                            .width(270.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = {
                            CustomTextField(
                                title = "Email",
                                value = email,
                                placeholder = "Exempel@gmail.com",
                                isError = emailError != null,
                                onValueChange = {
                                    validateEmail(it)
                                }
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
                                visualTransformation = PasswordVisualTransformation(),
                                isError = passwordError != null,
                                onValueChange = { validatePassword(it) },
                                trailingIcon = {
                                    val image = if (passwordVisible) R.drawable.outline_visibility_24 else R.drawable.outline_visibility_off_24
                                    IconButton(
                                        onClick = { passwordVisible = !passwordVisible },
                                        content = {
                                            Icon(
                                                modifier = Modifier
                                                    .size(20.dp),
                                                painter = painterResource(image),
                                                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                                tint = Color(0xFF368BF4)
                                            )
                                        }
                                    )
                                }
                            )
                            passwordError?.let {
                                Text(
                                    text = it,
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(start = 8.dp).align(Alignment.Start)
                                )
                            }
                            Spacer(modifier = Modifier.height(30.dp))
                            Box(
                                modifier = Modifier
                                    .clickable {
                                        validateEmail(email)
                                        validatePassword(password)
                                        if (emailError == null && passwordError == null) {
                                            isLoading = true
                                            login(
                                                email = email,
                                                password = password,
                                                onSuccess = {
                                                    isLoading = false
                                                    val intent = Intent(
                                                        createContext,
                                                        MainActivity::class.java
                                                    )
                                                    createContext.startActivity(intent)
                                                    Log.e(TAG, "SignInScreen: the hell" )
                                                    onBack()
                                                },
                                                onError = { error ->
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
                                            text = "LOGIN",
                                            color = Color.White,
                                            textAlign = TextAlign.Center,
                                            fontFamily = FontFamily(Font(R.font.poppinsreguler))
                                        )
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(60.dp))
                            Row(
                                content = {
                                    Text(
                                        text = "Don’t have an account? ",
                                        color = Color(0x54000000),
                                        fontSize = 10.sp,
                                        fontFamily = FontFamily(Font(R.font.poppinsreguler))
                                    )
                                    Text(
                                        modifier = Modifier.clickable {
                                            navController.navigate("registrasi")
                                        },
                                        text = "Sign Up",
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
private fun SignInScreenPrev() {
    SignInScreen(navController = rememberNavController(), onBack = {})
}