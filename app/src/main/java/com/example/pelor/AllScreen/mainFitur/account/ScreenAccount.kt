package com.example.pelor.AllScreen.mainFitur.account

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pelor.PartAccount.CardAccount
import com.example.pelor.PartAccount.CardEditProfile
import com.example.pelor.PartAccount.LanguagePickerDialog
import com.example.pelor.PartAccount.LanguageViewModel
import com.example.pelor.PartAccount.SecMission
import com.example.pelor.PartAccount.SecSettings
import com.example.pelor.PartAccount.alertDialogExit
import com.example.pelor.PopUpScreen.PopUpKeteranganMisi
import com.example.pelor.PopUpScreen.PopUpTheme
import com.example.pelor.R
import com.example.pelor.Service.ApiViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File

@Composable
fun ScreenAccount(navController: NavController? = null, logOut: () -> Unit) {
    val username = remember { mutableStateOf("") }
    val title = remember { mutableStateOf("") }
    val level = remember { mutableStateOf(1) }
    val xp = remember { mutableStateOf(0) }
    val imgUrl = remember { mutableStateOf("") }
    val titlemisi = remember { mutableStateOf("") }
    val submisi = remember { mutableStateOf("") }
    var showDialogMisiDetail by remember { mutableStateOf(false) }
    var showDialogTema by remember { mutableStateOf(false) }
    var showDialogExit by remember { mutableStateOf(false) }
    var showEdit by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var uri by remember { mutableStateOf<Uri?>(null) }
    val viewModel: ApiViewModel = viewModel()
    val misiSelesaiList = remember { mutableStateListOf<String>() }
    val misiSelesai by remember { derivedStateOf { misiSelesaiList.toList() } }
    val languageViewModel: LanguageViewModel = viewModel()
    var showDialogBahasa by remember { mutableStateOf(false) }
    val uidFlow = remember { UserPreferences(context).uid }
    val uid by uidFlow.collectAsState(initial = null)
    val currentUserId = uid.orEmpty()

    if (!currentUserId.isNullOrEmpty()) {
        DisposableEffect(currentUserId) {
            val listenerRegistration = FirebaseFirestore.getInstance()
                .collection("users")
                .document(currentUserId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null || snapshot == null || !snapshot.exists()) return@addSnapshotListener

                    username.value = snapshot.getString("username") ?: ""
                    title.value = snapshot.getString("title") ?: ""
                    level.value = snapshot.getLong("level")?.toInt() ?: 1
                    xp.value = snapshot.getLong("xp")?.toInt() ?: 0
                    imgUrl.value = snapshot.getString("profil") ?: ""

                    val list = snapshot.get("mission done") as? List<*> ?: emptyList<Any>()
                    misiSelesaiList.clear()
                    misiSelesaiList.addAll(list.filterIsInstance<String>())
                    Log.d("FirestoreMisi", "Raw: ${snapshot.get("mission done")}")
                }

            onDispose {
                listenerRegistration.remove()
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success && uri != null) {
            navController?.navigate("preview?imageUri=${Uri.encode(uri.toString())}&misi=${Uri.encode(titlemisi.value)}"){
                launchSingleTop = true
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        content = { padingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padingValues)
                    .background(MaterialTheme.colorScheme.background),
            ) {
                LazyColumn {
                    item {
                        CardAccount(
                            onClick = { navController?.popBackStack() },
                            name = username.value,
                            title = title.value,
                            imgUrl = imgUrl.value,
                            xp = xp.value,
                            ar = level.value,
                            maxXp = 10000,
                            onClickEdit = { showEdit = true }
                        )
                    }
                    item {
                        SecMission(
                            misiSelesaiList = misiSelesai,
                            onClick = { title, sub ->
                                titlemisi.value = title
                                submisi.value = sub
                                showDialogMisiDetail = true
                            }
                        )
                    }
                    item {
                        SecSettings(
                            onClickExit = { showDialogExit = true },
                            onClickBahasa = { showDialogBahasa = true },
                            onClickTema = { showDialogTema = true },
                            onClickUbahPassword = { },
                            onClickTentangKami = { }
                        )
                    }
                }

                if (showDialogBahasa) {
                    AnimatedVisibility(
                        visible = showDialogBahasa,
                        enter = fadeIn() + scaleIn(),
                        exit = fadeOut() + scaleOut()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))
                                .clickable { showDialogBahasa = false }
                        ) {
                            LanguagePickerDialog(
                                modifier = Modifier.align(Alignment.Center),
                                selected = languageViewModel.selectedLanguage,
                                onSelect = { lang ->
                                    languageViewModel.setLanguage(lang)
                                    showDialogBahasa = false
                                    (context as? Activity)?.recreate()
                                }
                            )
                        }
                    }
                }

                if (showDialogTema) {
                    AnimatedVisibility(
                        visible = showDialogTema,
                        enter = fadeIn() + scaleIn(),
                        exit = fadeOut() + scaleOut()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))
                                .clickable { showDialogTema = false }
                        ) {
                            PopUpTheme(
                                modifier = Modifier.align(Alignment.Center),
                                onClick = { showDialogTema = false }
                            )
                        }
                    }
                }

                if (showDialogMisiDetail) {
                    AnimatedVisibility(
                        visible = showDialogMisiDetail,
                        enter = fadeIn() + scaleIn(),
                        exit = fadeOut() + scaleOut()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))
                                .padding(30.dp)
                                .clickable { showDialogMisiDetail = false }
                        ) {
                            PopUpKeteranganMisi(
                                modifier = Modifier.align(Alignment.Center),
                                title = titlemisi.value,
                                misi = submisi.value,
                                keteranganHadiah = context.getString(R.string.popup_mission_reward_info),
                                onClick = {
                                    val file = File(context.externalCacheDir, "photo_${System.currentTimeMillis()}.jpg")
                                    val tempUri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
                                    uri = tempUri
                                    launcher.launch(tempUri)
                                    showDialogMisiDetail = false
                                }
                            )
                        }
                    }
                }

                if (showDialogExit) {
                    AnimatedVisibility(
                        visible = showDialogExit,
                        enter = fadeIn() + scaleIn(),
                        exit = fadeOut() + scaleOut()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))
                                .clickable { showDialogExit = false }
                        ) {
                            alertDialogExit(
                                modifier = Modifier.align(Alignment.Center),
                                onClickNo = { showDialogExit = false },
                                onClickYes = logOut
                            )
                        }
                    }
                }

                if (showEdit) {
                    AnimatedVisibility(
                        visible = showEdit,
                        enter = fadeIn() + scaleIn(),
                        exit = fadeOut() + scaleOut()
                    ) {
                        Box(Modifier.fillMaxSize()) {
                            CardEditProfile(
                                name = username.value,
                                title = title.value,
                                level = level.value.toString(),
                                imageUrl = imgUrl.value,
                                onClickBack = { showEdit = false }
                            )
                        }
                    }
                }
            }
        }
    )
}

val Context.userDataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {
    companion object {
        val UID_KEY = stringPreferencesKey("user_uid")
    }

    val uid: Flow<String?> = context.userDataStore.data.map { prefs ->
        prefs[UID_KEY]
    }

    suspend fun saveUid(uid: String) {
        context.userDataStore.edit { prefs ->
            prefs[UID_KEY] = uid
        }
    }

    suspend fun clearUid() {
        context.userDataStore.edit { prefs ->
            prefs.remove(UID_KEY)
        }
    }
}

@Preview
@Composable
private fun ScreeAccountPrev() {
    ScreenAccount(logOut = {})
}
