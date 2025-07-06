package com.example.pelor.gemifikasi

import android.net.Uri
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pelor.R
import com.example.pelor.Service.ApiViewModel
import kotlinx.coroutines.delay

@Composable
fun PreviewScreen(
    imageUri: String,
    viewModel: ApiViewModel,
    navController: NavController?,
    expectedLabel: String,
    onMissionResult: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val uploadResult = viewModel.uploadResult
    val uploadLabel = viewModel.uploadLabel

    LaunchedEffect(imageUri) {
        if (expectedLabel.isNotBlank()) {
            viewModel.uploadMission(
                context = context,
                uri = Uri.parse(imageUri),
                expectedLabel = expectedLabel,
                onResultReady = { success ->
                    onMissionResult(success)
                }
            )
        } else {
            viewModel.uploadImage(
                context = context,
                uri = Uri.parse(imageUri),
                onResultReady = { success ->
                    delay(5000)
                    onMissionResult(success)
                }
            )
        }
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
                        contentDescription = stringResource(id = R.string.back_button_description),
                        tint = Color(0xFF333333)
                    )
                }
                Text(
                    text = stringResource(id = R.string.preview_title),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333)
                )
            }

            if (!uploadLabel.isNullOrBlank() && uploadResult != null && uploadResult != "Upload failed") {
                Text(
                    text = uploadLabel,
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
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
                    modifier = Modifier.padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when (uploadResult) {
                            null, "Upload failed" -> stringResource(id = R.string.upload_failed_message)
                            "Uploading..." -> stringResource(id = R.string.uploading_message)
                            else -> uploadResult
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = when (uploadResult) {
                            null, "Upload failed" -> Color.Red
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
