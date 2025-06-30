package com.example.pelor.PartGelis

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.pelor.Service.DriverWithUser
import com.example.pelor.Service.ProfileRepository
import com.example.pelor.Service.ProfileRepository.fetchDriver
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SecGelis(navController: NavController?) {
    val driverList = remember { mutableStateListOf<DriverWithUser>() }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    LaunchedEffect(Unit) {
        fetchDriver(
            onSuccess = {
                driverList.clear()
                driverList.addAll(it)
                isLoading = false
            },
            onError = {
                errorMessage = it
                isLoading = false
            }
        )
    }

    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (errorMessage != null) {
        Text(
            text = "Error: $errorMessage",
            color = Color.Red,
            modifier = Modifier.padding(16.dp)
        )
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(driverList) { item ->
                CardGelis(
                    onClick = {
                        Log.d("CURRENT_USER_ID", currentUserId)
                        Log.d("DRIVER_UID", "Driver UID: ${item.user.uid}")
                        if (currentUserId.isNotEmpty() && item.user.uid.isNotEmpty()) {
                            ProfileRepository.createOrGetChat(
                                currentUserId = currentUserId,
                                driverUserId = item.user.uid,
                                onSuccess = { chatId ->
                                    navController?.navigate("chatPerson/$chatId")
                                },
                                onError = { error ->
                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                }
                            )
                        } else {
                            Toast.makeText(context, "User tidak valid", Toast.LENGTH_SHORT).show()
                        }
                    },
                    painter = rememberAsyncImagePainter(item.user.profil),
                    name = item.user.username,
                    codeGelis = item.driver.idDriver,
                    status = item.driver.status.toString(),
                    pengalaman = item.driver.pengalaman
                )
                HorizontalDivider()
            }
        }
    }
}

@Preview
@Composable
private fun SecGelisPrev() {
    SecGelis(rememberNavController())
}

