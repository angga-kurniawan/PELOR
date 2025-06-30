package com.example.pelor.AllScreen.mainFitur

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pelor.AllScreen.mainFitur.account.LogicAccount.getUserData
import com.example.pelor.PartDetail.SecDetail
import com.example.pelor.PartGelis.SecGelis
import com.example.pelor.PartHome.ComponentCatagory
import com.example.pelor.PartHome.CustomBottomSheetScaffold
import com.example.pelor.PartHome.PulauPenyengatMap
import com.example.pelor.PartHome.TopAppBarCustom
import com.example.pelor.R
import com.example.pelor.Service.ApiViewModel
import java.io.File

@Composable
fun HomeScreen(navController: NavController? = null) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTitle by remember { mutableStateOf<String?>(null) }
    var expandSheet by remember { mutableStateOf<(() -> Unit)?>(null) }
    var collapseSheet by remember { mutableStateOf<(() -> Unit)?>(null) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    val xp = remember { mutableIntStateOf(0) }
    val imgUrl = remember { mutableStateOf("") }
    val context = LocalContext.current
    var uri by remember { mutableStateOf<Uri?>(null) }
    val viewModel: ApiViewModel = viewModel()


    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success && uri != null) {
            viewModel.uploadImage(context, uri!!)
            navController?.navigate("preview?imageUri=${Uri.encode(uri.toString())}")
        }
    }

    getUserData(
        onSuccess = { userData ->
            Log.d("DataUser", "XP: ${userData.xp}")
            xp.intValue = userData.xp
            Log.d("DataUser", "Img Url: ${userData.profil}")
            imgUrl.value = userData.profil
        },
        onError = { error ->
            Log.e("DataUser", error)
        }
    )

    CustomBottomSheetScaffold(
        sheetContent = { isExpanded ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                content = {
                    Divider(
                        modifier = Modifier
                            .width(30.dp)
                            .align(Alignment.CenterHorizontally),
                        thickness = 2.dp
                    )
                    when {
                        selectedTitle != null -> {
                            SecDetail(
                                title = selectedTitle!!,
                                onClose = {
                                    selectedTitle = null
                                    collapseSheet?.invoke()
                                },
                                navController = navController
                            )
                            Log.e("SecDetail",selectedTitle.toString())
                        }
                        isExpanded -> {
                            SecGelis(
                                navController = navController,
                            )
                        }
                        else -> {
                            Box(
                                modifier = Modifier
                                    .height(26.dp)
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp),
                                contentAlignment = Alignment.TopCenter,
                                content = {
                                    Text(
                                        text = "Gelis",
                                        color = Color(0xFFCFCFCF),
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(Font(R.font.poppinsreguler)),
                                        modifier = Modifier.align(Alignment.CenterStart)
                                    )
                                }
                            )
                        }
                    }

                }
            )
        },
        onExpandRequest = { expandFn, collapseFn ->
            expandSheet = expandFn
            collapseSheet = collapseFn
        },
        floatingActionButton = {
            Column(
                content = {
                    FloatingActionButton(
                        modifier = Modifier
                            .shadow(shape = CircleShape, elevation = 1.dp)
                            .clip(CircleShape),
                        onClick = {
                            val file = File(context.externalCacheDir, "photo_${System.currentTimeMillis()}.jpg")
                            val tempUri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
                            uri = tempUri
                            launcher.launch(tempUri)
                        },
                        content = {
                            Icon(
                                modifier = Modifier.size(31.dp),
                                painter = painterResource(R.drawable.iconscan),
                                contentDescription = "",
                                tint = Color(0xFF368BF4)
                            )
                        },
                        containerColor = Color(0xFFF7F7F7)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    FloatingActionButton(
                        modifier = Modifier
                            .shadow(shape = CircleShape, elevation = 1.dp)
                            .clip(CircleShape),
                        onClick = {
                            navController?.navigate("historyChat")
                        },
                        content = {
                            Icon(
                                modifier = Modifier.size(31.dp),
                                painter = painterResource(R.drawable.iconchat),
                                contentDescription = "",
                                tint = Color(0xFFFFFEFE)
                            )
                        },
                        containerColor = Color(0xFF368BF4)
                    )
                }
            )
        },
        mainContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp),
                content = {
                    PulauPenyengatMap(
                        onNavigateToDetail = {
                            selectedTitle = it
                            expandSheet?.invoke()
                        },
                        selectedCategory = selectedCategory ?: "Sejarah"
                    )
                    Log.e("selected category",selectedCategory.toString())
                    Column(
                        content = {
                            TopAppBarCustom(
                                xp = xp.intValue,
                                maxXp = 10000,
                                query = searchQuery,
                                onQueryChange = {
                                    searchQuery = it
                                },
                                onClick = {
                                    navController?.navigate("account")
                                },
                                imgUrl = imgUrl.value
                            )
                            ComponentCatagory(
                                 setValue = {
                                     selectedCategory = it
                                 }
                            )
                        }
                    )
                }
            )
        }
    )
}

@Composable
fun PreviewScreen(imageUri: String, viewModel: ApiViewModel, navController: NavController?) {
    val context = LocalContext.current
    val uploadResult = viewModel.uploadResult

    LaunchedEffect(imageUri) {
        viewModel.uploadImage(context, Uri.parse(imageUri))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F4F4))
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController?.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = "Back",
                        tint = Color(0xFF333333)
                    )
                }
                Text(
                    text = "Preview Gambar",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333)
                )
            }

            AsyncImage(
                model = imageUri,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(5.dp))
                    .border(1.dp, Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                shape = RoundedCornerShape(5.dp),
                color = Color.White,
                shadowElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when (uploadResult) {
                            null -> "Gagal menganalisis gambar. Silakan coba lagi."
                            "Uploading..." -> "Sedang menganalisis gambar..."
                            else -> uploadResult
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = when (uploadResult) {
                            "Upload failed" -> Color.Red
                            "Uploading..." -> Color(0xFF444444)
                            else -> Color(0xFF1B5E20)
                        },
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun HomeScreenPrev() {
    HomeScreen(null)
}