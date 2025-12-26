package com.example.cardgame500.data.network.api

import com.example.cardgame500.data.network.model.AgeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/")
    suspend fun getAge(
        @Query("name") name: String
    ): AgeResponse
}
