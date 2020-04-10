package com.example.carousels

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import com.example.carousels.CarouselsApplication.Companion.context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*


object ApiFactory {
    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url()
            .newBuilder()
            .build()
        Log.d("API_FACTORY", newUrl.toString())
        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    val cookieJar = object : CookieJar {
        var cookies: MutableList<Cookie>? = null
        override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
            if (url.encodedPath().endsWith("authenticate")) {
                this.cookies = ArrayList(cookies)
                val cookieString = cookies.map {
                    "${it.name()}=${it.value()}"
                }
                val gson = Gson()
                val json = gson.toJson(cookies)
                val editor: SharedPreferences.Editor =
                    context.getSharedPreferences("mysharedpref", MODE_PRIVATE).edit()
                editor.putString("PREF_COOKIES", json).apply()
                editor.commit()
            }
        }

        override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
            return when {
                (!url.encodedPath()
                    .endsWith("authenticate") && cookies != null && cookies!!.isNotEmpty()) -> cookies as MutableList<Cookie>
                (!url.encodedPath().endsWith("authenticate")) -> shared()
                else -> mutableListOf()
            }
        }
    }

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .cookieJar(cookieJar)
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

private fun shared(): MutableList<Cookie> {
    val json = context.getSharedPreferences("mysharedpref", Context.MODE_PRIVATE)
        .getString("PREF_COOKIES", "")
    val gson = Gson()
    return gson.fromJson(
        json,
        object :
            TypeToken<MutableList<Cookie>>() {}.type
    )

}

