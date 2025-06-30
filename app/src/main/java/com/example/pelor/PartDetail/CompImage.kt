package com.example.pelor.PartDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.pelor.R
import com.example.pelor.Service.ImageLocation
import com.example.pelor.Service.ProfileRepository
import java.util.Locale

@Composable
fun CompImage(navController: NavController?,title: String) {
    val imageList = remember { mutableStateListOf<ImageLocation>() }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        ProfileRepository.fetchImages(
            collection = title.toLowerCase(),
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
        Box(Modifier.height(300.dp), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (errorMessage != null) {
        Text(text = "Gagal memuat gambar: $errorMessage", color = Color.Red)
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
                            .weight(weight = 1f)
                            .background(Color.White)
                            .clickable {
                                navController?.navigate("lihatSemuaGambar")
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
                                text = "Lihat Semua",
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
                            .weight(weight = 1f)
                            .background(Color(0xFF8AB6ED))
                            .clickable {
                                // TODO: Implementasikan logika upload di sini
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
                                text = "Tambahkan Foto",
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
fun LihatSemuaGambarScreen() {
    val imageList = remember { mutableStateListOf<ImageLocation>() }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        ProfileRepository.fetchImages(
            collection = "rumah hakim",
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

    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(imageList) { image ->
                Image(
                    painter = rememberAsyncImagePainter(image.imgUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Preview
@Composable
private fun CompImagePrev() {
    CompImage(rememberNavController(),"")
}