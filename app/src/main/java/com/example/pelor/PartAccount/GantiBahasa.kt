package com.example.pelor.PartAccount

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import com.example.pelor.R
import java.util.Locale



@Composable
fun LanguagePickerDialog(
    modifier: Modifier = Modifier,
    selected: String,
    onSelect: (String) -> Unit
) {
    val options = listOf(
        "id" to stringResource(R.string.indonesia),
        "en" to stringResource(R.string.inggris),
        "cn" to stringResource(R.string.china)
    )

    Box(
        modifier = modifier
            .width(300.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .border(1.dp, Color.LightGray)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = stringResource(R.string.bahasa),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            options.forEach { (code, label) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelect(code) }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selected == code,
                        onClick = { onSelect(code) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = label)
                }
            }
        }
    }
}
object LocaleHelper {
    fun setLocale(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        return context.createConfigurationContext(config)
    }
}
class LanguageViewModel(application: Application) : AndroidViewModel(application) {
    var selectedLanguage by mutableStateOf("id")
        private set

    init {
        val language = getApplication<Application>().getSharedPreferences("settings", Context.MODE_PRIVATE)
            .getString("language", "id") ?: "id"
        selectedLanguage = language
        applyLanguage()
    }

    fun setLanguage(code: String) {
        selectedLanguage = code
        val prefs = getApplication<Application>().getSharedPreferences("settings", Context.MODE_PRIVATE)
        prefs.edit().putString("language", code).apply()
        applyLanguage()
    }

    private fun applyLanguage() {
        val locale = Locale(selectedLanguage)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        getApplication<Application>().resources.updateConfiguration(config, getApplication<Application>().resources.displayMetrics)
    }
}

