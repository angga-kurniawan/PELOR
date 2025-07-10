package com.example.pelor.AllScreen.mainFitur

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.pelor.AllScreen.mainFitur.account.LogicAccount.getUserData
import com.example.pelor.PartDetail.SecDetail
import com.example.pelor.PartGelis.SecGelis
import com.example.pelor.PartHome.ComponentCatagory
import com.example.pelor.PartHome.CustomBottomSheetScaffold
import com.example.pelor.PartHome.LocalScrollingEnabled
import com.example.pelor.PartHome.PulauPenyengatMap
import com.example.pelor.PartHome.TopAppBarCustom
import com.example.pelor.PopUpScreen.LoadingUplodImageToFireStore
import com.example.pelor.R
import com.example.pelor.Service.UserPreferences
import com.example.pelor.gemifikasi.KategoriType
import java.io.File

@Composable
fun HomeScreen(navController: NavController? = null) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTitle by remember { mutableStateOf<String?>(null) }
    var expandSheet by remember { mutableStateOf<(() -> Unit)?>(null) }
    var collapseSheet by remember { mutableStateOf<(() -> Unit)?>(null) }
    var selectedCategory by remember { mutableStateOf<KategoriType?>(null) }
    val xp = remember { mutableIntStateOf(0) }
    val imgUrl = remember { mutableStateOf("") }
    val context = LocalContext.current
    var uri by remember { mutableStateOf<Uri?>(null) }
    val uidFlow = remember { UserPreferences(context).uid }
    val uid by uidFlow.collectAsState(initial = null)
    val isLoadingImageUpload = remember { mutableStateOf(false) }
    val loadingText = remember { mutableStateOf("Memuat...") }
    val acceptedUris = remember { mutableStateListOf<Uri>() }
    val rejectedUris = remember { mutableStateListOf<Uri>() }
    val isUploadFinished = remember { mutableStateOf(false) }
    var expandHalfSheet by remember { mutableStateOf<(() -> Unit)?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success && uri != null) {
            navController?.navigate("preview?imageUri=${Uri.encode(uri.toString())}"){
                launchSingleTop = true
            }
        }
    }

    getUserData(
        uid = uid,
        onSuccess = { userData ->
            xp.intValue = userData.xp
            imgUrl.value = userData.profil
        },
        onError = { error ->
            Log.e("DataUser", error)
        }
    )

    CustomBottomSheetScaffold(
        sheetContent = { isExpanded, isFullyExpanded ->

                when {
                    selectedTitle != null -> {
                        SecDetail(
                            title = selectedTitle!!,
                            onClose = {
                                selectedTitle = null
                                collapseSheet?.invoke()
                            },
                            navController = navController,
                            isLoadingImageUpload = isLoadingImageUpload,
                            loadingText = loadingText,
                            acceptedUris = acceptedUris,
                            rejectedUris = rejectedUris,
                        )
                    }

                    isExpanded -> {
                        SecGelis(navController = navController)
                    }

                    else -> {
                        Box(
                            modifier = Modifier
                                .height(26.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Text(
                                text = "Gelis",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.align(Alignment.CenterStart)
                            )
                        }
                    }
                }
        },
        onExpandRequest = { expandFull, collapse, expandHalf ->
            expandSheet = expandFull
            collapseSheet = collapse
            expandHalfSheet = expandHalf
        },
        floatingActionButton = {
            Column {
                FloatingActionButton(
                    modifier = Modifier
                        .shadow(1.dp, shape = CircleShape)
                        .clip(CircleShape),
                    onClick = {
                        val file = File(context.externalCacheDir, "photo_${System.currentTimeMillis()}.jpg")
                        val tempUri = FileProvider.getUriForFile(
                            context,
                            "${context.packageName}.fileprovider",
                            file
                        )
                        uri = tempUri
                        launcher.launch(tempUri)
                    },
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        modifier = Modifier.size(31.dp),
                        painter = painterResource(R.drawable.iconscan),
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                FloatingActionButton(
                    modifier = Modifier
                        .shadow(1.dp, shape = CircleShape)
                        .clip(CircleShape),
                    onClick = {
                        navController?.navigate("historyChat")
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.background
                ) {
                    Icon(
                        modifier = Modifier.size(31.dp),
                        painter = painterResource(R.drawable.iconchat),
                        contentDescription = null
                    )
                }
            }
        },
        mainContent = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                PulauPenyengatMap(
                    onNavigateToDetail = {
                        selectedTitle = it
                        expandHalfSheet?.invoke()
                    },
                    selectedCategory = selectedCategory ?: KategoriType.SEJARAH,
                    searchQuery = searchQuery
                )

                Column {
                    TopAppBarCustom(
                        xp = xp.intValue,
                        maxXp = 10000,
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        onClick = { navController?.navigate("account") },
                        imgUrl = imgUrl.value
                    )
                    ComponentCatagory(setValue = { selectedCategory = it })
                }
            }
        }
    )

    if (isLoadingImageUpload.value) {
        val finished = acceptedUris.isNotEmpty() || rejectedUris.isNotEmpty()
        LoadingUplodImageToFireStore(
            loadingText = loadingText.value,
            acceptedUris = acceptedUris,
            rejectedUris = rejectedUris,
            uploadFinished = finished,
            onDismiss = {
                isLoadingImageUpload.value = false
                acceptedUris.clear()
                rejectedUris.clear()
                loadingText.value = "Memuat..."
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPrev() {
    HomeScreen(null)
}