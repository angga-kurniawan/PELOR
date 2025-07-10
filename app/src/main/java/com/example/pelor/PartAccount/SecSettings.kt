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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    bahasaYangDigunakan: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalDivider(color = Color(0xFFDFDFDF))
        Text(
            text = stringResource(R.string.pengaturan),
            color = Color(0xFF5C5C5C),
            modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
        )

        CardSettings(title = stringResource(R.string.bahasa), sub = bahasaYangDigunakan, onClick = {onClickBahasa()})
        CardSettings(title = stringResource(R.string.tema), onClick = {onClickTema()})
//        CardSettings(title = stringResource(R.string.ubah_password), onClick = {onClickUbahPassword()})
//        CardSettings(title = stringResource(R.string.tentang_kami), onClick = {onClickTentangKami()})
        CardSettings(
            title = stringResource(R.string.keluar),
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
    val colorScheme = MaterialTheme.colorScheme
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable { onClick() }
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            color = colorScheme.onBackground,
            fontSize = 13.sp
        )
        sub?.let {
            Text(
                text = it,
                color = colorScheme.onSurface.copy(alpha = 0.7f),
                fontSize = 10.sp
            )
        }
        icon?.invoke()
    }

    HorizontalDivider(color = colorScheme.onSurface.copy(alpha = 0.1f))
}

@Preview(showBackground = true)
@Composable
private fun SecSettingsPrev() {
    SecSettings({},{},{},{},{},"")
}