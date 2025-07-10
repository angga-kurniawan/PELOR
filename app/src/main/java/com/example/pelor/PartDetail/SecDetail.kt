package com.example.pelor.PartDetail

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pelor.R
import com.example.pelor.gemifikasi.getKategoriDetails

@Composable
fun SecDetail(
    title: String,
    onClose: () -> Unit,
    navController: NavController?,
    isLoadingImageUpload: MutableState<Boolean>,
    loadingText: MutableState<String>,
    acceptedUris: SnapshotStateList<Uri>,
    rejectedUris: SnapshotStateList<Uri>,
) {
    val context = LocalContext.current
    val kategoriDetails = remember { getKategoriDetails(context) }

    val filteredItemsWithCategory = kategoriDetails
        .flatMap { kategori ->
            kategori.items.map { item ->
                item to kategori.category
            }
        }
        .filter { (item, _) -> item.title == title }
        .distinctBy { (item, _) -> item.title }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        IconButton(onClick = onClose) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            ) {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .padding(3.dp),
                    painter = painterResource(R.drawable.baseline_close_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(15.dp))

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(filteredItemsWithCategory) { (sejarahItem, category) ->
            Text(
                text = category,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(20.dp))

            CompImage(
                navController = navController,
                title = title,
                isLoadingImageUpload = isLoadingImageUpload,
                loadingText = loadingText,
                acceptedUris = acceptedUris,
                rejectedUris = rejectedUris,
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(0.3f),
                    text = "${stringResource(R.string.sejarah_next)} ${sejarahItem.title}:",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                IconButton(
                    modifier = Modifier.padding(start = 50.dp),
                    onClick = { /* TODO */ }
                ) {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                            .fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(15.dp),
                            painter = painterResource(R.drawable.iconsound),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = sejarahItem.sejarah,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 30.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SecDetailPrev() {
//    SecDetail(
//        title = "Balai Adat Melayu",
//        onClose = {
//
//        },
//        navController = rememberNavController()
//    )
}