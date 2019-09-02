package com.example.carousels

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface Api {
    @GET
    fun getConfigAsync(@Url url:String): Deferred<Response<List<Carousel>>>
}