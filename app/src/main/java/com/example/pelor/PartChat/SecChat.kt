package com.example.pelor.PartChat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.pelor.AllScreen.mainFitur.account.UserPreferences
import com.example.pelor.Service.ChatPreview
import com.example.pelor.Service.ProfileRepository.fetchChatParticipants
import com.example.pelor.Service.formatChatTimestamp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SecChat(navController: NavController?, modifier: Modifier = Modifier) {
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val chatList = remember { mutableStateListOf<ChatPreview>() }

    val context = LocalContext.current
    val uidFlow = remember { UserPreferences(context).uid }
    val currentUserId by uidFlow.collectAsState(initial = null)

    val estimatedSize = if (chatList.isNotEmpty()) chatList.size else 5

    LaunchedEffect(Unit) {
        if (!currentUserId.isNullOrEmpty()) {
            fetchChatParticipants(
                currentUserId = currentUserId!!,
                onSuccess = { allChats ->
                    chatList.clear()
                    chatList.addAll(allChats.filter { it.chatTerakhir.isNotBlank() })
                    isLoading = false
                },
                onError = {
                    errorMessage = it
                    isLoading = false
                }
            )
        } else {
            errorMessage = "User belum login"
            isLoading = false
        }
    }

    when {
        isLoading -> LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(estimatedSize) {
                CardPersonCardShimmer()
            }
        }

        errorMessage != null -> Text(
            text = "Error: $errorMessage",
            color = Color.Red,
            modifier = Modifier.padding(16.dp)
        )

        else -> LazyColumn(modifier = modifier.fillMaxSize()) {
            items(chatList) { item ->
                CardPersonCard(
                    name = item.user.username,
                    chat = item.chatTerakhir,
                    painter = rememberAsyncImagePainter(item.user.profil),
                    code = item.idDriver ?: "Pengembara",
                    time = formatChatTimestamp(item.timestampTerakhir),
                    onClick = {
                        navController?.navigate("chatPerson/${item.chatId}")
                    }
                )
            }
        }
    }
}

@Composable
fun CardPersonCardShimmer() {
    val colorScheme = MaterialTheme.colorScheme
    val shimmerColor = colorScheme.surfaceVariant.copy(alpha = 0.5f)
    val shimmerHighlight = PlaceholderHighlight.shimmer(colorScheme.onSurface.copy(alpha = 0.3f))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(colorScheme.surface),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(55.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .placeholder(
                            visible = true,
                            highlight = shimmerHighlight,
                            color = shimmerColor
                        )
                )
                Box(
                    modifier = Modifier
                        .padding(end = 3.dp, bottom = 3.dp)
                        .clip(CircleShape)
                        .size(10.dp)
                        .background(shimmerColor)
                        .align(Alignment.BottomEnd)
                )
            }

            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .height(14.dp)
                                .width(80.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .placeholder(
                                    visible = true,
                                    highlight = shimmerHighlight,
                                    color = shimmerColor
                                )
                        )

                        Box(
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .clip(CircleShape)
                                .size(5.dp)
                                .background(shimmerColor)
                        )

                        Box(
                            modifier = Modifier
                                .height(12.dp)
                                .width(50.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .placeholder(
                                    visible = true,
                                    highlight = shimmerHighlight,
                                    color = shimmerColor
                                )
                        )
                    }

                    Box(
                        modifier = Modifier
                            .height(12.dp)
                            .width(40.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .placeholder(
                                visible = true,
                                highlight = shimmerHighlight,
                                color = shimmerColor
                            )
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .height(12.dp)
                        .fillMaxWidth(0.8f)
                        .clip(RoundedCornerShape(4.dp))
                        .placeholder(
                            visible = true,
                            highlight = shimmerHighlight,
                            color = shimmerColor
                        )
                )
            }
        }
    }
    HorizontalDivider(color = shimmerColor)
}

@Preview(showBackground = true)
@Composable
private fun SecChatPrev() {
    SecChat(navController = rememberNavController())
}