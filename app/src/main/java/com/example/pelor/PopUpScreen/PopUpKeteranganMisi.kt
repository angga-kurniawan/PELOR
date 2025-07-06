package com.example.pelor.PopUpScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pelor.R

@Composable
fun PopUpKeteranganMisi(
    modifier: Modifier = Modifier,
    title: String,
    misi: String,
    keteranganHadiah: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clickable(
                enabled = false,
                onClick = {  }
            )
            .clip(shape = RoundedCornerShape(10.dp))
            .width(356.dp)
            .wrapContentHeight()
            .background(Color(0xFF313C56))
            .padding(5.dp)
            .border(
                width = 1.dp,
                color = Color(0xFF67663C),
                shape = RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.Center,
        content = {
            Column(
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 10.dp,
                    top = 30.dp
                ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = title,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.protestrevolutionregular))
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Left,
                        text = misi,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.protestrevolutionregular))
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Left,
                        color = Color.White,
                        text = keteranganHadiah,
                        fontFamily = FontFamily(Font(R.font.protestrevolutionregular))
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        modifier = Modifier
                            .align(Alignment.End)
                            .width(120.dp)
                            .border(
                                width = 1.dp,
                                color = Color(0xFF67663C),
                                shape = CircleShape
                            ),
                        onClick = { onClick() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                        ),
                        content = {
                            Text(
                                text = "Upload",
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.protestrevolutionregular))
                            )
                        }
                    )
                }
            )
        }
    )
}

@Composable
fun alert(
    title: String,
    misi: String,
    keteranganHadiah: String,
    onClick: () -> Unit,
    onDismis: () -> Unit
) {
    AlertDialog(
        containerColor = Color(0xFF313C56),
        onDismissRequest = onDismis,
        confirmButton = {

        },
        dismissButton = {

        },
        title = { Text(title) },
        text = { Text(misi) }
    )
}

@Preview(showBackground = true)
@Composable
private fun alertPrev() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = {
            alert (
                title = "Penjelajah Media",
                misi = "Unggah 4 foto tempat bersejarah dari Pulau Penyengat. Tempat yang bisa kamu foto antara lain:\n" +
                        "\t•\tMakam Raja Ali Haji\n" +
                        "\t•\tMasjid Sultan Riau\n" +
                        "\t•\tIstana Kantor\n" +
                        "\t•\tBalai Adat, atau situs sejarah lainnya di Pulau Penyengat.",
                keteranganHadiah = "Kamu akan mendapatkan +200 XP setelah mengunggah 4 foto valid.",
                onClick = {},
                onDismis = {}
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PopUpKeteranganMisiPrev() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = {
            PopUpKeteranganMisi(
                title = "Penjelajah Media",
                misi = "Unggah 4 foto tempat bersejarah dari Pulau Penyengat. Tempat yang bisa kamu foto antara lain:\n" +
                        "\t•\tMakam Raja Ali Haji\n" +
                        "\t•\tMasjid Sultan Riau\n" +
                        "\t•\tIstana Kantor\n" +
                        "\t•\tBalai Adat, atau situs sejarah lainnya di Pulau Penyengat.",
                keteranganHadiah = "Kamu akan mendapatkan +200 XP setelah mengunggah 4 foto valid.",
                onClick = {}
            )
        }
    )
}