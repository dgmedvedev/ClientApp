package com.demo.clientapplication.data

import com.demo.clientapplication.presentation.MainViewModel
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

                val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(BASE_URL)
                    .build()

                val apiService = retrofit.create(ApiService::class.java)
                instanceApiService = apiService

                return apiService
            }
        }
    }
}