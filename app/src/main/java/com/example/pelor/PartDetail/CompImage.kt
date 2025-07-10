package com.example.pelor.PartDetail

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.pelor.R
import com.example.pelor.Service.ApiViewModel
import com.example.pelor.Service.ImageLocation
import com.example.pelor.Service.ProfileRepository
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun CompImage(
    navController: NavController?,
    title: String,
    isLoadingImageUpload: MutableState<Boolean>,
    loadingText: MutableState<String>,
    acceptedUris: SnapshotStateList<Uri>,
    rejectedUris: SnapshotStateList<Uri>,
) {
    val imageList = remember { mutableStateListOf<ImageLocation>() }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val viewModel: ApiViewModel = viewModel()
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        viewModel.uploadDetectedImagesToFirestore(
            context = context,
            uris = uris,
            locationName = when (title.lowercase()) {
                "malay traditional hall" -> "balai adat melayu"
                "kursi hill fort" -> "benteng bukit kursi"
                "physicians building" -> "gedung tabib"
                "engku putris tomb" -> "makam engku putri"
                "raja ali hajis tomb" -> "makam raja ali haji"
                "sultan riau grand mosque" -> "masjid raya sultan riau"
                "judges house" -> "rumah hakim"
                else -> title.lowercase()
            },
            loadingState = isLoadingImageUpload,
            loadingText = loadingText,
            acceptedUris = acceptedUris,
            rejectedUris = rejectedUris,
            onUploadDone = {}
        )
    }

    LaunchedEffect(Unit) {
        ProfileRepository.fetchImages(
            collection = title.lowercase(),
            onSuccess = {
                imageList.clear()
                imageList.addAll(it)
                isLoading = false
            },
            onError = {
                errorMessage = it
                isLoading = false
            }
        )
    }

    if (isLoading) {
        LazyRow(modifier = Modifier.height(300.dp)) {
            items(3) {
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(5.dp))
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = Color.LightGray
                        )
                )
                Spacer(modifier = Modifier.width(5.dp))
            }
        }
    } else if (errorMessage != null) {
        Text(
            text = "${stringResource(R.string.gagal_memuat_gambar)} : $errorMessage",
            color = Color.Red
        )
    } else {
        val displayedImages = imageList.take(3)

        LazyRow(modifier = Modifier.height(300.dp)) {
            items(displayedImages) { image ->
                Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .fillMaxHeight()
                        .width(300.dp),
                    painter = rememberAsyncImagePainter(image.imgUrl),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(5.dp))
            }

            item {
                Column {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .width(150.dp)
                            .weight(1f)
                            .background(Color.White)
                            .clickable {
                                navController?.navigate("lihatSemuaGambar/${title.lowercase()}") {
                                    launchSingleTop = true
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = rememberAsyncImagePainter(
                                model = imageList.getOrNull(2)?.imgUrl ?: ""
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(R.drawable.blacktransparant),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(R.drawable.icongaleri),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = stringResource(R.string.lihat_semua),
                                fontSize = 10.sp,
                                color = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .width(150.dp)
                            .weight(1f)
                            .background(Color(0xFF8AB6ED))
                            .clickable {
                                launcher.launch("image/*")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(R.drawable.iconfoto),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = stringResource(R.string.tambahkan_foto),
                                fontSize = 10.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LihatSemuaGambarScreen(navController: NavController?, lokasi: String) {
    val imageList = remember { mutableStateListOf<ImageLocation>() }
    var isLoading by remember { mutableStateOf(true) }
    var selectedImage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        ProfileRepository.fetchImages(
            collection = lokasi,
            onSuccess = {
                imageList.clear()
                imageList.addAll(it)
                isLoading = false
            },
            onError = {
                isLoading = false
            }
        )
    }

    Box(
        modifier = Modifier
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 42.dp, start = 15.dp),
                contentAlignment = Alignment.TopStart
            ) {
                IconButton(
                    onClick = { navController?.popBackStack() },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFEEEEEE), shape = CircleShape)
                        .clip(CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Tutup",
                        tint = Color(0xFF333333)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (isLoading) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(6) {
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.LightGray)
                                .placeholder(
                                    visible = true,
                                    highlight = PlaceholderHighlight.shimmer()
                                )
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(imageList) { image ->
                        Image(
                            painter = rememberAsyncImagePainter(image.imgUrl),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { selectedImage = image.imgUrl }
                        )
                    }
                }
            }
        }

        if (selectedImage != null) {
            Dialog(onDismissRequest = { selectedImage = null }) {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val maxWidth = constraints.maxWidth.toFloat()
                    val maxHeight = constraints.maxHeight.toFloat()

                    val scale = remember { mutableStateOf(1f) }
                    val offsetX = remember { mutableStateOf(0f) }
                    val offsetY = remember { mutableStateOf(0f) }

                    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
                        scale.value *= zoomChange
                        offsetX.value += offsetChange.x
                        offsetY.value += offsetChange.y
                    }

                    Box(
                        modifier = Modifier
                            .graphicsLayer(
                                scaleX = scale.value,
                                scaleY = scale.value,
                                translationX = offsetX.value,
                                translationY = offsetY.value
                            )
                            .then(
                                if (maxWidth < maxHeight) {
                                    Modifier.fillMaxWidth()
                                } else {
                                    Modifier.fillMaxHeight()
                                }
                            )
                            .clip(RoundedCornerShape(8.dp))
                            .transformable(state)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(selectedImage),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }

                    IconButton(
                        onClick = { selectedImage = null },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .size(40.dp)
                            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Tutup Gambar",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CompImagePrev() {
//    CompImage(rememberNavController(), "")
}