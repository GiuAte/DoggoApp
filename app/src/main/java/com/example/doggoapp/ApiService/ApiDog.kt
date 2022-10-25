package com.example.doggoapp.ApiService

import com.example.doggoapp.doggoData.DoggoData
import retrofit2.Response
import retrofit2.http.GET

interface ApiDog {

    @GET("api/breeds/image/random")
    suspend fun randomDogImages(): Response<DoggoData>
}