package com.demo.clientapplication.data

import com.google.gson.JsonObject
import retrofit2.http.GET

interface ApiService {

    @GET("todo")
    suspend fun getJson(): JsonObject

    @GET("home")
    suspend fun getString(): String
}