package com.example.roombooking.data.api

import com.example.roombooking.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val roomApi: RoomApi = retrofit.create(RoomApi::class.java)
}
