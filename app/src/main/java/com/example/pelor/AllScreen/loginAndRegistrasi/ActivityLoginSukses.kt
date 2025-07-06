package com.example.pelor.AllScreen.loginAndRegistrasi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pelor.AllScreen.loginAndRegistrasi.ui.theme.PelorTheme
import com.example.pelor.AllScreen.loginAndRegistrasi.ui.theme.ThemeViewModel

class ActivityLoginSukses : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val isDarkMode by themeViewModel.isDarkMode.collectAsState(initial = false)

            PelorTheme(isDarkMode = isDarkMode) {
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
}
