package com.example.pelor.AllScreen.mainFitur

import android.Manifest
import android.content.Context
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pelor.AllScreen.SecChatPerson
import com.example.pelor.AllScreen.loginAndRegistrasi.ActivityLoginSukses
import com.example.pelor.AllScreen.loginAndRegistrasi.ui.theme.PelorTheme
import com.example.pelor.AllScreen.loginAndRegistrasi.ui.theme.ThemeViewModel
import com.example.pelor.AllScreen.mainFitur.account.ScreenAccount
import com.example.pelor.PartAccount.LocaleHelper
import com.example.pelor.PartDetail.LihatSemuaGambarScreen
import com.example.pelor.R
import com.example.pelor.Service.ApiViewModel
import com.example.pelor.Service.AuthLoginAndRegistrasi.logout
import com.example.pelor.gemifikasi.PreviewScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val prefs = newBase.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val langCode = prefs.getString("language", "id") ?: "id"
        val context = LocaleHelper.setLocale(newBase, langCode)
        super.attachBaseContext(context)
    }

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val viewModel: ApiViewModel = viewModel()
            val navController = rememberAnimatedNavController()
            val themeViewModel: ThemeViewModel = viewModel()
            val isDarkMode by themeViewModel.isDarkMode.collectAsState(initial = false)

            PelorTheme(isDarkMode = isDarkMode) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.CAMERA),
                        100
                    )
                }

                if (viewModel.showLevelUpDialog) {
                    AlertDialog(
                        onDismissRequest = { viewModel.dismissLevelUpDialog() },
                        title = { Text(stringResource(id = R.string.dialog_title_level_up)) },
                        text = { Text(stringResource(id = R.string.dialog_message_level_up)) },
                        confirmButton = {
                            TextButton(onClick = { viewModel.dismissLevelUpDialog() }) {
                                Text(stringResource(id = R.string.dialog_button_ok))
                            }
                        }
                    )
                }

                AnimatedNavHost(
                    navController = navController,
                    startDestination = "home",
                    builder = {
                        composable("home") {
                            HomeScreen(navController = navController)
                        }

                        composable(
                            "lihatSemuaGambar/{collectionName}",
                            enterTransition = {
                                slideInHorizontally(
                                    initialOffsetX = { -it },
                                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                                )
                            },
                            exitTransition = {
                                slideOutHorizontally(
                                    targetOffsetX = { it },
                                    animationSpec = tween(300, easing = FastOutLinearInEasing)
                                )
                            },
                            popEnterTransition = {
                                slideInHorizontally(
                                    initialOffsetX = { it },
                                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                                )
                            },
                            popExitTransition = {
                                slideOutHorizontally(
                                    targetOffsetX = { -it },
                                    animationSpec = tween(300, easing = FastOutLinearInEasing)
                                )
                            },
                            arguments = listOf(navArgument("collectionName") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val collectionName = backStackEntry.arguments?.getString("collectionName") ?: "default"
                            LihatSemuaGambarScreen(navController, collectionName)
                        }

                        composable(
                            "preview?imageUri={imageUri}&misi={misi}",
                            arguments = listOf(
                                navArgument("imageUri") { type = NavType.StringType },
                                navArgument("misi") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                    nullable = true
                                }
                            )
                        ) { backStackEntry ->
                            val imageUri = backStackEntry.arguments?.getString("imageUri") ?: ""
                            val misi = backStackEntry.arguments?.getString("misi") ?: ""
                            PreviewScreen(
                                imageUri = imageUri,
                                viewModel = viewModel,
                                navController = navController,
                                expectedLabel = misi,
                                onResult = { success ->
                                    Toast.makeText(
                                        context,
                                        if (success)
                                            context.getString(R.string.toast_upload_success)
                                        else
                                            context.getString(R.string.toast_upload_failed),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable("account",
                            enterTransition = {
                                slideInHorizontally(
                                    initialOffsetX = { -it },
                                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                                )
                            },
                            exitTransition = {
                                slideOutHorizontally(
                                    targetOffsetX = { it },
                                    animationSpec = tween(300, easing = FastOutLinearInEasing)
                                )
                            },
                            popEnterTransition = {
                                slideInHorizontally(
                                    initialOffsetX = { it },
                                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                                )
                            },
                            popExitTransition = {
                                slideOutHorizontally(
                                    targetOffsetX = { -it },
                                    animationSpec = tween(300, easing = FastOutLinearInEasing)
                                )
                            }
                        ) {
                            ScreenAccount(
                                navController = navController,
                                logOut = {
                                    Log.d("ActivityAccount", "Logout initiated")
                                    logout(
                                        onSuccess = {
                                            Toast.makeText(
                                                this@MainActivity,
                                                getString(R.string.toast_logout_success),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            val loginIntent = Intent(
                                                this@MainActivity,
                                                ActivityLoginSukses::class.java
                                            ).apply {
                                                flags =
                                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            }
                                            startActivity(loginIntent)
                                            Log.d(
                                                "ActivityAccount",
                                                "Navigating to NavigasiActivityLoginAndRegistrasi"
                                            )
                                            finish()
                                        },
                                        onError = { error ->
                                            Log.e("Logout Error", error)
                                        },
                                        context = context
                                    )
                                }
                            )
                        }

                        composable("historyChat",
                            enterTransition = {
                                slideInHorizontally(
                                    initialOffsetX = { it },
                                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                                )
                            },
                            exitTransition = {
                                slideOutHorizontally(
                                    targetOffsetX = { -it },
                                    animationSpec = tween(300, easing = FastOutLinearInEasing)
                                )
                            },
                            popEnterTransition = {
                                slideInHorizontally(
                                    initialOffsetX = { -it },
                                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                                )
                            },
                            popExitTransition = {
                                slideOutHorizontally(
                                    targetOffsetX = { it },
                                    animationSpec = tween(300, easing = FastOutLinearInEasing)
                                )
                            }
                        ) {
                            ChatScreen(navController = navController)
                        }

                        composable("chatPerson/{chatId}",
                            enterTransition = {
                                slideInHorizontally(
                                    initialOffsetX = { it },
                                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                                )
                            },
                            exitTransition = {
                                slideOutHorizontally(
                                    targetOffsetX = { -it },
                                    animationSpec = tween(300, easing = FastOutLinearInEasing)
                                )
                            },
                            popEnterTransition = {
                                slideInHorizontally(
                                    initialOffsetX = { -it },
                                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                                )
                            },
                            popExitTransition = {
                                slideOutHorizontally(
                                    targetOffsetX = { it },
                                    animationSpec = tween(300, easing = FastOutLinearInEasing)
                                )
                            }
                        ) {
                            val chatId = it.arguments?.getString("chatId") ?: return@composable
                            SecChatPerson(chatId = chatId, navController = navController)
                        }
                    }
                )
            }
        }
    }
}

