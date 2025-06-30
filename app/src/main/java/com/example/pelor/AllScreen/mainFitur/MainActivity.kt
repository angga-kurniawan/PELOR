package com.example.pelor.AllScreen.mainFitur

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pelor.AllScreen.SecChatPerson
import com.example.pelor.AllScreen.loginAndRegistrasi.ActivityLoginSukses
import com.example.pelor.Service.AuthLoginAndRegistrasi.logout
import com.example.pelor.AllScreen.mainFitur.account.ScreenAccount
import com.example.pelor.PartDetail.LihatSemuaGambarScreen
import com.example.pelor.Service.ApiViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: ApiViewModel = viewModel()
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    100
                )
            }
            val navController = rememberAnimatedNavController()
            AnimatedNavHost(
                navController = navController,
                startDestination = "home",
                builder = {
                    composable(
                        route = "home",
                        content = {
                            HomeScreen(navController = navController)
                        }
                    )
                    composable(
                        route = "lihatSemuaGambar",
                        enterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { -it },
                                animationSpec = tween(
                                    durationMillis = 400,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        },
                        exitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutLinearInEasing
                                )
                            )
                        },
                        popEnterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 400,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        },
                        popExitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { -it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutLinearInEasing
                                )
                            )
                        },
                        content = {
                            LihatSemuaGambarScreen()
                        }
                    )
                    composable(
                        route = "preview?imageUri={imageUri}",
                        arguments = listOf(navArgument("imageUri") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val imageUri = backStackEntry.arguments?.getString("imageUri") ?: ""
                        PreviewScreen(imageUri = imageUri, viewModel = viewModel, navController = navController)
                    }
                    composable(
                        route = "account",
                        enterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { -it },
                                animationSpec = tween(
                                    durationMillis = 400,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        },
                        exitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutLinearInEasing
                                )
                            )
                        },
                        popEnterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 400,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        },
                        popExitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { -it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutLinearInEasing
                                )
                            )
                        },
                        content = {
                            ScreenAccount(
                                navController = navController,
                                logOut = {
                                    Log.d("ActivityAccount", "Logout initiated")
                                    logout(
                                        onSuccess = {
                                            Toast.makeText(this@MainActivity, "Logout Succses", Toast.LENGTH_SHORT).show()
                                            val loginIntent = Intent(this@MainActivity, ActivityLoginSukses::class.java).apply {
                                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            }
                                            startActivity(loginIntent)
                                            Log.d("ActivityAccount", "Navigating to NavigasiActivityLoginAndRegistrasi")
                                            finish()
                                        },
                                        onError = { error ->
                                            Log.e("Logout Error",error)
                                        }
                                    )
                                }
                            )
                        }
                    )
                    composable(
                        route = "historyChat",
                        enterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 400,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        },
                        exitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { -it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutLinearInEasing
                                )
                            )
                        },
                        popEnterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { -it },
                                animationSpec = tween(
                                    durationMillis = 400,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        },
                        popExitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutLinearInEasing
                                )
                            )
                        },
                        content = {
                            ChatScreen(navController = navController)
                        }
                    )
                    composable(
                        route = "chatPerson/{chatId}",
                        enterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 400,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        },
                        exitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { -it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutLinearInEasing
                                )
                            )
                        },
                        popEnterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { -it },
                                animationSpec = tween(
                                    durationMillis = 400,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        },
                        popExitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutLinearInEasing
                                )
                            )
                        },
                        content = {
                            val chatId = it.arguments?.getString("chatId") ?: return@composable
                            SecChatPerson(chatId = chatId, navController = navController)
                        }
                    )
                }
            )
        }
    }
}

