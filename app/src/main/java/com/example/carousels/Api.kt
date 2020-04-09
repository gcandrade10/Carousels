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
        "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36",
        "Referer: http://192.168.0.16:8080/",
        "Accept-Language: es-US,es-419;q=0.9,es;q=0.8",
        "Cookie: streama_remember_me=YWRtaW46MTU4NzY1MTA4NzEwMzplMDc2NTg0NjE0ZjVmNzA5Y2IyZjU4YjNmYzQ0YzQ3YQ; JSESSIONID=77F74E8FBED35433314F211583D8A42F"
    )
    @GET("dash/listContinueWatching.json")
    fun getlistContinueWatching(): Deferred<Response<List<MyTitle>>>

    @Headers(
        "Connection: keep-alive",
        "Accept: application/json, text/plain, */*",
        "profileId: 1",
        "X-Requested-With: XMLHttpRequest",
        "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36",
        "Referer: http://192.168.0.16:8080/",
        "Accept-Language: es-US,es-419;q=0.9,es;q=0.8",
        "Cookie: streama_remember_me=YWRtaW46MTU4NzY1MTA4NzEwMzplMDc2NTg0NjE0ZjVmNzA5Y2IyZjU4YjNmYzQ0YzQ3YQ; JSESSIONID=77F74E8FBED35433314F211583D8A42F"

    )
    @GET("video/show.json")
    fun getShow(@Query("id") id: Int): Deferred<Response<Show>>
}