package com.example.pelor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pelor.AllScreen.loginAndRegistrasi.ActivityLoginSukses
import com.example.pelor.AllScreen.mainFitur.MainActivity
import com.example.pelor.ui.theme.PelorTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

class SplashScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaunchedEffect(Unit) {
                val isUserLoggedIn = FirebaseAuth.getInstance().currentUser != null
                Log.e("User Status", if (isUserLoggedIn) "Logged In" else "Not Logged In")
                delay(3000)

                val targetActivity = if (isUserLoggedIn) MainActivity::class.java
                else ActivityLoginSukses::class.java

                startActivity(Intent(this@SplashScreen, targetActivity))
                finish()
            }
        }
    }
}
