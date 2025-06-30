package com.example.pelor.Service

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ApiViewModel : ViewModel() {

    private val repo = UploadRepository()
    var uploadResult by mutableStateOf<String?>(null)
        private set

    fun uploadImage(context: Context, uri: Uri) {
        viewModelScope.launch {
            uploadResult = "Uploading..."
            val result = repo.uploadImage(context, uri)

            val confidence = result?.confidence ?: 0.0
            val treshold = result?.threshold_check ?: 0.0

            Log.e("API RESULT", "Confidence: $confidence")
            Log.e("API RESULT", "threshold_check: $treshold")

            if (confidence >= 80.0) {
                ProfileRepository.addXpToUser(
                    amount = 50,
                    onSuccess = {
                        Log.d("XP", "XP berhasil ditambahkan")
                    },
                    onError = {
                        Log.e("XP", "Gagal menambahkan XP: $it")
                    }
                )
            }

            uploadResult = result?.threshold_check ?: "Upload failed"
        }
    }
}