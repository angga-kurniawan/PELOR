package com.example.pelor.PartAccount

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pelor.R

@Composable
fun SecSettings(
    onClickExit: () -> Unit,
    onClickBahasa : () -> Unit,
    onClickUbahPassword : () -> Unit,
    onClickTentangKami : () -> Unit,
    onClickTema : () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalDivider(color = Color(0xFFDFDFDF))
        Text(
            text = "Pengaturan",
            color = Color(0xFF5C5C5C),
            modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
        )

        CardSettings(title = "Bahasa", sub = "indonesia", onClick = {onClickBahasa()})
        CardSettings(title = "Tema", onClick = {onClickTema()})
        CardSettings(title = "Ubah Password", onClick = {onClickUbahPassword()})
        CardSettings(title = "Tentang Kami", onClick = {onClickTentangKami()})
        CardSettings(
            title = "Keluar",
            icon = {
                Icon(
                    painter = painterResource(R.drawable.exiticon),
                    contentDescription = null,
                    tint = Color(0xFFCA0000),
                    modifier = Modifier.size(15.dp)
                )
            },
            onClick = {
                onClickExit()
            }
        )
    }
}

@Composable
fun CardSettings(
    title: String,
    sub: String? = null,
    icon: (@Composable () -> Unit)? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable {
                onClick()
            }
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            Text(text = title, color = Color(0xFF5C5C5C), fontSize = 13.sp)
            sub?.let { sub ->
                Text(text = sub, color = Color(0xFFB4B4B4), fontSize = 10.sp)
            }
            icon?.invoke()
        }
    )
    HorizontalDivider(color = Color(0xFFDFDFDF))
}

@Preview(showBackground = true)
@Composable
private fun SecSettingsPrev() {
    SecSettings({},{},{},{},{})
}