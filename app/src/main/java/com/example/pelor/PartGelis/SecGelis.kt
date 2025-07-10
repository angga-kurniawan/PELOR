package com.example.pelor.PartGelis

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.pelor.Service.DriverWithUser
import com.example.pelor.Service.ProfileRepository
import com.example.pelor.Service.ProfileRepository.fetchDriver
import com.example.pelor.Service.UserPreferences

@Composable
fun SecGelis(navController: NavController?) {
    val driverList = remember { mutableStateListOf<DriverWithUser>() }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val uidFlow = remember { UserPreferences(context).uid }
    val currentUserId by uidFlow.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        fetchDriver(
            currentUserId = currentUserId,
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


    when {
        isLoading -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(5) {
                    Column {
                        ShimmerGelisItem()
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider(color = MaterialTheme.colorScheme.outlineVariant)
                    }
                }
            }
        }
        errorMessage != null -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "⚠ ${errorMessage.orEmpty()}",
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(remember { object : NestedScrollConnection {} })
            ) {
                items(driverList) { item ->
                    Column {
                        CardGelis(
                            onClick = {
                                if (!currentUserId.isNullOrEmpty() && item.user.uid.isNotEmpty()) {
                                    ProfileRepository.createOrGetChat(
                                        currentUserId = currentUserId!!,
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
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider(color = MaterialTheme.colorScheme.outlineVariant)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SecGelisPrev() {
    SecGelis(rememberNavController())
}