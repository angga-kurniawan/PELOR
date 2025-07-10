package com.example.pelor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.BounceInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pelor.AllScreen.loginAndRegistrasi.ActivityLoginSukses
import com.example.pelor.AllScreen.loginAndRegistrasi.ui.theme.PelorTheme
import com.example.pelor.AllScreen.loginAndRegistrasi.ui.theme.ThemeViewModel
import com.example.pelor.AllScreen.mainFitur.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class SplashScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current
            val themeViewModel: ThemeViewModel = viewModel()
            val isDarkMode by themeViewModel.isDarkMode.collectAsState(initial = false)

            PelorTheme(isDarkMode = isDarkMode) {

                val logoOffsetY = remember { Animatable(1000f) } // start dari bawah
                LaunchedEffect(Unit) {
                    logoOffsetY.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(
                            durationMillis = 1200,
                            easing = {
                                BounceInterpolator().getInterpolation(it)
                            }
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.logopelor),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .offset(y = logoOffsetY.value.dp)
                            .size(150.dp)
                    )
                }

                val authPrefs = remember { AuthPreferences(context) }
                val isLoggedInState = remember { mutableStateOf(false) }
                LaunchedEffect(Unit) {
                    authPrefs.isLoggedIn.collect { savedPref ->
                        val firebaseUser = FirebaseAuth.getInstance().currentUser
                        val isLoggedIn = savedPref || firebaseUser != null

                        isLoggedInState.value = isLoggedIn

                        delay(3000)

                        val target = if (isLoggedIn) MainActivity::class.java
                        else ActivityLoginSukses::class.java

                        context.startActivity(Intent(context, target))
                        (context as Activity).finish()
                    }
                }
            }
        }
    }
}


val Context.authDataStore by preferencesDataStore(name = "auth_prefs")

class AuthPreferences(private val context: Context) {
    companion object {
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val USER_UID = stringPreferencesKey("user_uid")
    }

    val isLoggedIn: Flow<Boolean> = context.authDataStore.data.map { prefs ->
        prefs[IS_LOGGED_IN] ?: false
    }

    val userUid: Flow<String?> = context.authDataStore.data.map { prefs ->
        prefs[USER_UID]
    }

    suspend fun setLoggedIn(value: Boolean) {
        context.authDataStore.edit { prefs ->
            prefs[IS_LOGGED_IN] = value
        }
    }

    suspend fun setUserUid(uid: String) {
        context.authDataStore.edit { prefs ->
            prefs[USER_UID] = uid
        }
    }
}