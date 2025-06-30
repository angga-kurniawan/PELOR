package com.example.pelor.AllScreen.loginAndRegistrasi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class ActivityLoginSukses : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "login"
            ) {
                composable("login") {
                    SignInScreen(navController = navController, onBack = { finish() })
                }
                composable("registrasi") {
                    SignUpScreen(navController = navController)
                }
            }
        }
    }
}
