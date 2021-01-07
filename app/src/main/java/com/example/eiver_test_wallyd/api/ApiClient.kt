package com.example.eiver_test_wallyd.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    fun getClient(): ApiService {
        val client = OkHttpClient.Builder()
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
    }
}