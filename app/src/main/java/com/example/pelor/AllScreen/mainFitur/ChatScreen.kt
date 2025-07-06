package com.example.pelor.AllScreen.mainFitur

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pelor.PartChat.SecChat
import com.example.pelor.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController? = null) {
    val colorScheme = MaterialTheme.colorScheme

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.border(
                    width = 1.dp,
                    shape = RoundedCornerShape(bottomStart = 5.dp, bottomEnd = 5.dp),
                    color = colorScheme.outline.copy(alpha = 0.3f)
                ),
                title = {
                    Text(
                        text = "Chat",
                        color = colorScheme.onSurface,
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.poppinsreguler))
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController?.popBackStack()
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = null,
                            tint = colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.surface
                )
            )
        },
        content = { paddingValues ->
            SecChat(
                modifier = Modifier.padding(paddingValues),
                navController = navController
            )
        }
    )
}

@Preview
@Composable
private fun ChatScreenPrev() {
    ChatScreen()
}