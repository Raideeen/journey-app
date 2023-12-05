package com.example.journey.service

import com.example.journey.model.CountryEntity
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

interface RestCountriesService {
    @GET("v3.1/region/{region}?fields=name,capital,flags")
    suspend fun getCountriesByRegion(@Path("region") region: String): Response<List<CountryEntity>>

    companion object {
        fun create(): RestCountriesService {
            val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            val client = OkHttpClient.Builder().addInterceptor(logging).build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://restcountries.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(RestCountriesService::class.java)
        }
    }
}