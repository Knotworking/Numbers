package com.knotworking.numbers.api

import com.knotworking.numbers.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created on 04.12.17.
 */
object RestClient {

    private val BASE_URL = "https://openexchangerates.org/api/"

    fun <S> createService(serviceClass: Class<S>): S {
        val client: OkHttpClient
        val clientBuilder = OkHttpClient.Builder()
        val builder: Retrofit.Builder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())

        clientBuilder.addInterceptor(createLoggingInterceptor())

        clientBuilder.connectTimeout(1, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES)
        client = clientBuilder.build()
        builder.baseUrl(BASE_URL)

        val retrofit = builder.client(client).build()
        return retrofit.create(serviceClass)
    }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = when {
            (BuildConfig.DEBUG) -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

}