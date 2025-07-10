package com.example.pelor.Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

    // API SECRET : KovkNrk_Y0nL4_gpwP9Kwcm3_3E
    // API KEY : 422274471229478
    // URL : cloudinary://<your_api_key>:<your_api_secret>@drwwnzu1r
object ApiClient {

    private const val BASE_URL = "https://vnsd13-nwspd.hf.space/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

object CloudinaryApiClient {
    private const val CLOUDINARY_BASE_URL = "https://api.cloudinary.com/v1_1/drwwnzu1r/" // Ganti `drwwnzu1r` sesuai cloud_name kamu

    val apiService: CloudinaryApiService by lazy {
        Retrofit.Builder()
            .baseUrl(CLOUDINARY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CloudinaryApiService::class.java)
    }
}
