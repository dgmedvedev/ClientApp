package com.demo.clientapplication.data

import com.demo.clientapplication.presentation.MainViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiFactory private constructor() {

    companion object {
        private const val BASE_URL =
            "http://${MainViewModel.SERVER_HOST}:${MainViewModel.SERVER_HTTP_PORT}/"

        @Volatile
        private var instanceApiService: ApiService? = null

        fun getInstanceApiService(): ApiService {
            instanceApiService?.let { return it }
            synchronized(ApiFactory::class.java) {
                instanceApiService?.let { return it }

                val gson = GsonBuilder()
                    .setLenient()
                    .create()

                val okHttpClient = OkHttpClient.Builder()
                    .readTimeout(3, TimeUnit.SECONDS)
                    .connectTimeout(1, TimeUnit.SECONDS)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build()

                val apiService = retrofit.create(ApiService::class.java)
                instanceApiService = apiService

                return apiService
            }
        }
    }
}