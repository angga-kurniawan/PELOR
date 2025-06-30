package com.example.pelor.PartDetail

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pelor.R
import com.example.pelor.gemifikasi.kategoriDetails

@Composable
fun SecDetail(
    title: String,
    onClose: () -> Unit,
    navController: NavController?
) {
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
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Text(
                text = title,
                fontSize = 17.sp
            )
            IconButton(
                onClick = onClose,
                content = {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = Color(0x4DD8D8D8)),
                        content = {
                            Icon(
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(3.dp),
                                painter = painterResource(R.drawable.baseline_close_24),
                                contentDescription = null,
                                tint = Color(0xFFD8D8D8)
                            )
                        }
                    )
                }
            )
        }
    )
    Spacer(modifier = Modifier.height(15.dp))
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp),
        content = {
            items(filteredItemsWithCategory) { (sejarahItem, category) ->
                Text(
                    text = category,
                    color = Color(0xFF7D7D7D)
                )
                Spacer(modifier = Modifier.height(20.dp))
                CompImage(navController = navController, title = title)
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Text(
                            modifier = Modifier.weight(0.3f),
                            text = "Sejarah mengenai ${sejarahItem.title}:",
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            modifier = Modifier
                                .padding(start = 50.dp),
                            onClick = {

                            },
                            content = {
                                Box(
                                    modifier = Modifier
                                        .background(Color(0x3DD9D9D9))
                                        .fillParentMaxSize(),
                                    contentAlignment = Alignment.Center,
                                    content = {
                                        Icon(
                                            modifier = Modifier.size(15.dp),
                                            painter = painterResource(R.drawable.iconsound),
                                            contentDescription = null,
                                            tint = Color(0xFF5B85CE)
                                        )
                                    }
                                )
                            }
                        )
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier.padding(bottom = 30.dp),
                    text = sejarahItem.sejarah
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SecDetailPrev() {
    SecDetail(
        title = "Balai Adat Melayu",
        onClose = {

        },
        navController = rememberNavController()
    )
}