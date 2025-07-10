package com.example.pelor.PopUpScreen

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pelor.AllScreen.loginAndRegistrasi.ui.theme.ThemeViewModel
import com.example.pelor.AllScreen.loginAndRegistrasi.ui.theme.ThemeViewModelFactory
import com.example.pelor.R

@Composable
fun PopUpTheme(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val themeViewModel: ThemeViewModel = viewModel(
        factory = ThemeViewModelFactory(context.applicationContext as Application)
    )

    val isDarkTheme by themeViewModel.isDarkMode.collectAsState(initial = true)
    val borderColor = MaterialTheme.colorScheme.outline
    val surfaceColor = MaterialTheme.colorScheme.surface
    val primaryColor = MaterialTheme.colorScheme.primary
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface

    Column(
        modifier = modifier
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(surfaceColor)
            .width(250.dp)
            .wrapContentHeight()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.tema),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                color = onSurfaceColor
            )
            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(30.dp),
                onClick = onClick,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = primaryColor.copy(alpha = 0.1f),
                    contentColor = primaryColor
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_close_24),
                    contentDescription = "Close",
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Divider(color = borderColor)
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ThemeOption(
                isSelected = !isDarkTheme,
                backgroundColor = Color.White,
                onClick = { themeViewModel.setDarkMode(false) }
            )

            ThemeOption(
                isSelected = isDarkTheme,
                backgroundColor = Color.Black,
                onClick = { themeViewModel.setDarkMode(true) }
            )

            ThemeOption(
                isSelected = false,
                backgroundColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                onClick = null,
                isComingSoon = true
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PopUpThemePrev() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = {
            PopUpTheme(
                onClick = {}
            )
        }
    )
}