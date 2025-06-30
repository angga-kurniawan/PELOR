package com.example.pelor.PartChat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.pelor.Service.ChatPreview
import com.example.pelor.Service.ProfileRepository.fetchChatParticipants
import com.example.pelor.Service.formatChatTimestamp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SecChat(navController: NavController?, modifier: Modifier = Modifier) {
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val chatList = remember { mutableStateListOf<ChatPreview>() }
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()

    LaunchedEffect(Unit) {
        if (currentUserId.isNotEmpty()) {
            fetchChatParticipants(
                currentUserId = currentUserId,
                onSuccess = {
                    chatList.clear()
                    chatList.addAll(it)
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
        isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
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

@Preview(showBackground = true)
@Composable
private fun SecChatPrev() {
    SecChat(navController = rememberNavController())
}