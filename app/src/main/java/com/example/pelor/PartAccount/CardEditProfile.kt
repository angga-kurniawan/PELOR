package com.example.pelor.PartAccount

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pelor.AllScreen.mainFitur.account.LogicAccount
import com.example.pelor.R
import com.example.pelor.Service.ProfileRepository
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@SuppressLint("RememberReturnType")
@Composable
fun CardEditProfile(
    level: String,
    name: String,
    title: String,
    imageUrl: String,
    onClickBack: () -> Unit
) {
    val BgMap = mapOf(
        "1" to R.drawable.bg_baal_with_shadow
    )
    val focusManager = LocalFocusManager.current
    val tabs = listOf("Profil", "Latar Belakang", "Gelar")
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    var userName by remember { mutableStateOf(name) }
    val avatarMap = remember { mutableStateOf<Map<String, String>>(emptyMap()) }
    var selectedAvatar = remember { mutableStateOf(imageUrl) }
    var selectedBg by remember { mutableStateOf(R.drawable.bg_baal_with_shadow) }
    val titleMap = remember { mutableStateOf<Map<String, String>>(emptyMap()) }
    var selectedtitle by remember { mutableStateOf(title) }
    val context = LocalContext.current
    LaunchedEffect(true) {
        ProfileRepository.fetchProfiles(
            onSuccess = {
                avatarMap.value = ProfileRepository.profilMap
            },
            onError = { Log.e("Firestore", it) }
        )
        ProfileRepository.fetchTitle(
            onSuccess = {
                titleMap.value = ProfileRepository.titleMap
            },
            onError = { Log.e("Firestore", it) }
        )
    }

    Column(
        modifier = Modifier
            .background(Color.Black.copy(alpha = 0.6f))
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSection(
            selectedAvatar = selectedAvatar.value,
            name = userName,
            title = selectedtitle,
            onNameChange = { userName = it },
            focusManager = focusManager,
            onClickBack = onClickBack,
            onSave = { username, avatarUrl, selectedTitle ->
                val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()
                LogicAccount.updateUserProfile(
                    userId = currentUserId,
                    username = username,
                    profil = avatarUrl,
                    title = selectedTitle,
                    onSuccess = {
                        Toast.makeText(context, "Berhasil disimpan", Toast.LENGTH_SHORT).show()
                        onClickBack()
                    },
                    onError = {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        )

        TabSection(tabs, pagerState) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(it)
            }
        }

        PagerSection(
            pagerState = pagerState,
            tabs = tabs,
            avatarMap = avatarMap.value,
            level = level,
            selectedAvatar = selectedAvatar.value,
            onAvatarSelected = { selectedAvatar.value = it },
            titleMap = titleMap.value,
            selectedTitle = selectedtitle,
            onTitleSelected = { selectedtitle = it },
            onBgSelected = { selectedBg = it },
            bgMap = BgMap,
            selectedBg = selectedBg
        )
    }
}

@Composable
fun HeaderSection(
    selectedAvatar: String,
    name: String,
    title: String,
    onNameChange: (String) -> Unit,
    focusManager: FocusManager,
    onClickBack: () -> Unit,
    onSave: (String, String, String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(R.drawable.bg_baal_with_shadow),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        Column {
            Row(modifier = Modifier.padding(top = 20.dp, start = 10.dp)) {
                    Box(contentAlignment = Alignment.Center) {
                        Image(
                            modifier = Modifier.size(120.dp),
                            painter = painterResource(R.drawable.bgprofile),
                            contentDescription = null
                        )
                        AsyncImage(
                            modifier = Modifier
                                .clip(CircleShape)
                                .border(2.dp, Color.White, CircleShape)
                                .size(100.dp),
                            contentDescription = null,
                            model = selectedAvatar
                        )
                    }

                Column {
                    Box(modifier = Modifier.padding(horizontal = 10.dp)) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            painter = painterResource(R.drawable.bg_shadow_small),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds
                        )
                        Column(modifier = Modifier.padding(10.dp)) {
                            TextField(
                                value = name,
                                onValueChange = onNameChange,
                                placeholder = {
                                    Text(
                                        "UserName Tidak Boleh Kosong",
                                        color = Color.Red,
                                        maxLines = 1,
                                        fontSize = 10.sp
                                    )
                                },
                                singleLine = true,
                                maxLines = 1,
                                colors = TextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    unfocusedContainerColor = Color.Black.copy(alpha = 0.2f),
                                    focusedContainerColor = Color.Black.copy(alpha = 0.7f),
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                    errorIndicatorColor = Color.Transparent,
                                ),
                                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                                keyboardActions = KeyboardActions(
                                    onDone = { focusManager.clearFocus() }
                                ),
                            )
                            Spacer(
                                modifier = Modifier
                                    .size(0.dp)
                                    .focusable()
                            )
                            Text(
                                modifier = Modifier.padding(top = 7.dp),
                                text = title,
                                color = Color.Cyan,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(end = 25.dp),
                horizontalArrangement = Arrangement.End,
                content = {
                    Button(
                        onClick = {
                            onSave(name, selectedAvatar, title)
                        },
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black.copy(alpha = 0.5f)
                        ),
                        content = {
                            Text(text = "Simpan")
                        }
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Button(
                        onClick = {
                            onClickBack()
                        },
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black.copy(alpha = 0.5f)
                        ),
                        content = {
                            Text(text = "Batal")
                        }
                    )
                }
            )
        }
    }
}

@Composable
fun TabSection(tabs: List<String>, pagerState: PagerState, onTabSelected: (Int) -> Unit) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.Black.copy(alpha = 0.7f),
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = { onTabSelected(index) },
                text = { Text(title, color = Color.White) }
            )
        }
    }
}

@Composable
fun PagerSection(
    pagerState: PagerState,
    tabs: List<String>,
    avatarMap: Map<String, String>,
    bgMap: Map<String, Int>,
    titleMap: Map<String, String>,
    level: String,
    selectedAvatar: String,
    selectedTitle: String,
    selectedBg: Int,
    onAvatarSelected: (String) -> Unit,
    onTitleSelected: (String) -> Unit,
    onBgSelected: (Int) -> Unit,
) {
    HorizontalPager(
        count = tabs.size,
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        when (page) {
            0 -> AvatarGridPage(
                avatarMap = avatarMap,
                level = level,
                selectedAvatar = selectedAvatar,
                onAvatarSelected = onAvatarSelected
            )

            1 -> BgEdit(
                bgMap = bgMap,
                level = level,
                selectedBg = selectedBg,
                onBgSelected = onBgSelected
            )

            2 -> GelarEdit(
                selectedTitle = selectedTitle,
                titleMap = titleMap,
                level = level,
                onTitleSelected = onTitleSelected,
            )
        }
    }
}

@Composable
fun AvatarGridPage(
    avatarMap: Map<String, String>,
    level: String,
    selectedAvatar: String,
    onAvatarSelected: (String) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(avatarMap.toList()) { (levelImg, imgUrl) ->
            val isLocked = level.toInt() < levelImg.toInt()
            val isSelected = selectedAvatar == imgUrl

            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(enabled = !isLocked) {
                        onAvatarSelected(imgUrl)
                    }
            ) {
                AsyncImage(
                    model = imgUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                if (isLocked) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color.Black.copy(alpha = 0.8f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            fontSize = 20.sp,
                            text = "Lv.$levelImg",
                            fontFamily = FontFamily(Font(R.font.poppinsreguler)),
                            color = Color.White
                        )
                    }
                }

                if (isSelected && !isLocked) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color.Black.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Terpilih",
                            tint = Color.Green.copy(alpha = 0.6f),
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GelarEdit(
    titleMap: Map<String, String>,
    level: String,
    selectedTitle: String,
    onTitleSelected: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(titleMap.toList()) { (requiredLevel, title) ->
            val isLocked = level.toInt() < requiredLevel.toInt()
            val isSelected = selectedTitle == title

            Box(
                modifier = Modifier
                    .clickable(enabled = !isLocked) {
                        onTitleSelected(title)
                    }
                    .height(70.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )

                if (isLocked) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color.Black.copy(alpha = 0.6f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            fontSize = 20.sp,
                            text = "Lv.$requiredLevel",
                            fontFamily = FontFamily(Font(R.font.poppinsreguler)),
                            color = Color.White
                        )
                    }
                }

                if (isSelected && !isLocked) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color.Black.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Terpilih",
                            tint = Color.Green.copy(alpha = 0.6f),
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BgEdit(
    bgMap: Map<String, Int>,
    level: String,
    selectedBg: Int,
    onBgSelected: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(bgMap.toList()) { (levelImg, resId) ->
            val isLocked = level.toInt() < levelImg.toInt()
            val isSelected = selectedBg == resId

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(enabled = !isLocked) {
                        onBgSelected(resId)
                    }
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = resId),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )

                if (isLocked) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color.Black.copy(alpha = 0.8f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Lv.$levelImg",
                            fontFamily = FontFamily(Font(R.font.poppinsreguler)),
                            color = Color.White
                        )
                    }
                }

                if (isSelected && !isLocked) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color.Black.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Terpilih",
                            tint = Color.Green.copy(alpha = 0.6f),
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GelarEditPrev() {
    val titleMap = mapOf(
        "1" to "Pengembara Pemula",
        "5" to "Pengembara Senior",
        "10" to "Peneliti Sejarah Pemula",
        "15" to "Peneliti Sejarah Senior",
        "20" to "Ahli Sejarah"
    )
    val focusManager = LocalFocusManager.current

    GelarEdit(titleMap = titleMap, level = "10", selectedTitle = "", onTitleSelected = {})
}

@Preview(showBackground = true)
@Composable
private fun CardEditProfilePrev() {
    CardEditProfile(level = "1", name = "angga", title = "Peneliti Sejarah Pemula", imageUrl = "", onClickBack = {})
}