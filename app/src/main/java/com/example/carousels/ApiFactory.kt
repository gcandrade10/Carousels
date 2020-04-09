package com.example.carousels

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiFactory {
    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url()
            .newBuilder()
            .build()
        Log.d("API_FACTORY",newUrl.toString())
        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()

    val converter = MoshiConverterFactory.create().asLenient()
    private fun retrofit(): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("http://192.168.0.16:8080/")
        .addConverterFactory(converter)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val api: Api = retrofit().create(Api::class.java)
}
