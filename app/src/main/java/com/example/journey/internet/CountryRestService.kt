package com.example.journey.internet

import com.example.journey.model.CountryEntity
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Interface for the REST Countries API service.
 * This interface defines the HTTP operations that can be performed on the API.
 * It uses Retrofit to create the API service.
 */
interface RestCountriesService {

    /**
     * Fetches a list of countries by region from the API.
     * This method is a suspend function, which means it should be called from a coroutine or another suspend function.
     *
     * @param region The region to fetch countries for.
     * @return A Response object containing a list of CountryEntity objects.
     */
    @GET("v3.1/region/{region}?fields=name,capital,flags")
    suspend fun getCountriesByRegion(@Path("region") region: String): Response<List<CountryEntity>>

    companion object {
        /**
         * Creates an instance of the RestCountriesService.
         * This method sets up the Retrofit instance with the base URL for the API and the Gson converter factory.
         * It also sets up an OkHttpClient with a logging interceptor for logging HTTP request and response data.
         *
         * @return An instance of the RestCountriesService.
         */
        fun create(): RestCountriesService {
            // Create a logging interceptor and set its level to BODY
            val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            // Create an OkHttpClient and add the logging interceptor to it
            val client = OkHttpClient.Builder().addInterceptor(logging).build()

            // Create a Retrofit instance with the base URL for the API, the Gson converter factory, and the OkHttpClient
            val retrofit = Retrofit.Builder()
                .baseUrl("https://restcountries.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            // Create and return an instance of the RestCountriesService
            return retrofit.create(RestCountriesService::class.java)
        }
    }
}