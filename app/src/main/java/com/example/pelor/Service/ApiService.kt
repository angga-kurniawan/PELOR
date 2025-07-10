package com.example.pelor.Service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("predict/")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<UploadResponse>
}

interface CloudinaryApiService {
    @Multipart
    @POST("image/upload")
    suspend fun uploadToCloudinary(
        @Part image: MultipartBody.Part,
        @Part("upload_preset") preset: RequestBody // jika pakai unsigned upload
    ): Response<CloudinaryResponse>
}
