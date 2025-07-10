package com.example.pelor.gemifikasi

import android.net.Uri
import android.util.Log
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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
    onResult: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val uploadResult = viewModel.uploadResult
    val uploadLabel = viewModel.uploadLabel

    LaunchedEffect(imageUri) {
        if (!viewModel.hasUploaded) {
            viewModel.setUploaded()
            if (expectedLabel.isNotBlank()) {
                viewModel.uploadMission(
                    context = context,
                    uri = Uri.parse(imageUri),
                    expectedLabel = expectedLabel,
                    onResultReady = { success ->
                        onResult(success)
                        Log.d("UPLOAD_CHECK", "uploadMission triggered. hasUploaded = true")
                    }
                )
            } else {
                viewModel.uploadImage(
                    context = context,
                    uri = Uri.parse(imageUri),
                    onResultReady = { success ->
                        delay(5000)
                        onResult(success)
                        Log.d("UPLOAD_CHECK", "uploadImage triggered. hasUploaded = true")
                    }
                )
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetUploadFlag()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(
                    text = stringResource(id = R.string.preview_title),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            if (!uploadLabel.isNullOrBlank() && uploadResult != null && uploadResult != "Upload failed") {
                Text(
                    text = uploadLabel,
                    color = MaterialTheme.colorScheme.onBackground,
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
                color = MaterialTheme.colorScheme.surface,
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

@Preview
@Composable
private fun PreviewScreenPrev() {
    PreviewScreen(
        navController = rememberNavController(),
        expectedLabel = "",
        viewModel = viewModel(),
        onResult = {},
        imageUri = ""
    )
}
