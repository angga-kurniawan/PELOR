package com.example.pelor.AllScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pelor.AllScreen.mainFitur.account.UserPreferences
import com.example.pelor.PartPersonChat.CardDialogCharPerson
import com.example.pelor.PartPersonChat.TextFieldInputChatPerson
import com.example.pelor.PartPersonChat.TopAppBarPersonChat
import com.example.pelor.Service.Message
import com.example.pelor.Service.ProfileRepository
import com.example.pelor.Service.User
import com.example.pelor.Service.formatChatTimestamp
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SecChatPerson(
    chatId: String,
    navController: NavController
) {
    val colorScheme = MaterialTheme.colorScheme
    val context = LocalContext.current
    val uidFlow = remember { UserPreferences(context).uid }
    val currentUserId by uidFlow.collectAsState(initial = null)
    val messages = remember { mutableStateListOf<Message>() }
    var input by remember { mutableStateOf("") }
    var lawanUser by remember { mutableStateOf<User?>(null) }
    var driverCode by remember { mutableStateOf("") }

    LaunchedEffect(chatId) {
        ProfileRepository.listenToMessages(
            chatId = chatId,
            currentUserId = currentUserId!!,
            onUserLoaded = { user, code ->
                lawanUser = user
                driverCode = code ?: "Pengembara"
            },
            onUpdate = { updatedMessages ->
                messages.clear()
                messages.addAll(updatedMessages)
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBarPersonChat(
                navController = navController,
                userName = lawanUser?.username ?: "Chat",
                code = driverCode,
                imgUrl = lawanUser?.profil.orEmpty()
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
            ) {
                items(messages) { msg ->
                    CardDialogCharPerson(
                        input = msg.message,
                        time = formatChatTimestamp(msg.timestamp),
                        person = msg.idPengirim == currentUserId
                    )
                }
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
                    .navigationBarsPadding()
                    .background(colorScheme.surface)
            ) {
                TextFieldInputChatPerson(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    input = input,
                    onValueChange = { input = it },
                    onSend = {
                        if (input.isNotBlank()) {
                            val newMessage = Message(
                                idPengirim = currentUserId!!,
                                message = input,
                                timestamp = Timestamp.now()
                            )
                            ProfileRepository.sendMessage(chatId, newMessage) {
                                input = ""
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    )
}

@Preview
@Composable
private fun SecChatPersonPrev() {
    SecChatPerson("", navController = rememberNavController())
}