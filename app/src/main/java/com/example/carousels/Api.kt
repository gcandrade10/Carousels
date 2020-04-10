package com.example.carousels

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface Api {
    @GET
    fun getConfigAsync(@Url url: String): Deferred<Response<List<Carousel>>>

    @Headers(
        "Connection: keep-alive",
        "Accept: application/json, text/plain, */*",
        "profileId: 1",
        "X-Requested-With: XMLHttpRequest",
        "Referer: http://192.168.0.16:8080/",
        "Accept-Language: es-US,es-419;q=0.9,es;q=0.8"
    )
    @GET("dash/listContinueWatching.json")
    fun getlistContinueWatching(): Deferred<Response<List<MyTitle>>>

    @Headers(
        "Connection: keep-alive",
        "Accept: application/json, text/plain, */*",
        "profileId: 1",
        "X-Requested-With: XMLHttpRequest",
        "Referer: http://192.168.0.16:8080/",
        "Accept-Language: es-US,es-419;q=0.9,es;q=0.8"

    )
    @GET("video/show.json")
    fun getShow(@Query("id") id: Int): Deferred<Response<Show>>

    @Headers(
        "Connection: keep-alive",
        "Accept: application/json, text/plain, */*",
        "profileId: 1",
        "X-Requested-With: XMLHttpRequest",
        "Referer: http://192.168.0.16:8080/",
        "Accept-Language: es-US,es-419;q=0.9,es;q=0.8"
    )
    @GET("viewingStatus/save.json")
    fun saveCurrentTime(
        @Query("videoId") videoId: Int,
        @Query("currentTime") currentTime: Long
    ): Deferred<Response<SaveResponse>>
}