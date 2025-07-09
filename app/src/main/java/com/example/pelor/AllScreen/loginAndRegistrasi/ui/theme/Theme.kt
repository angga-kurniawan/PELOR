package com.example.pelor.AllScreen.loginAndRegistrasi.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF368BF4),           // Biru utama
    onPrimary = Color.White,                     // Teks/icon di atas primary
    secondary = Color(0xFF90CAF9),         // Biru terang
    onSecondary = Color.Black,
    background = Color(0xFFF5F5F5),         // Background utama (abu terang)
    onBackground = Color(0xFF1C1C1E),       // Teks utama
    surface = Color.White,                        // Kartu/panel
    onSurface = Color(0xFF1C1C1E),          // Teks/icon di atas surface
    error = Color(0xFFD32F2F),
    onError = Color.White
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF368BF4),           // Tetap biru untuk konsistensi
    onPrimary = Color.Black,
    secondary = Color(0xFF4A90E2),         // Biru redup
    onSecondary = Color.White,
    background = Color(0xFF121212),        // Background utama
    onBackground = Color(0xffffffff),      // Teks utama
    surface = Color(0xFF1E1E1E),           // Kartu/panel
    onSurface = Color(0xFFE0E0E0),
    error = Color(0xFFCF6679),
    onError = Color.Black
)

@Composable
fun PelorTheme(
    isDarkMode: Boolean,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (isDarkMode) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        isDarkMode -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = if (isDarkMode) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}