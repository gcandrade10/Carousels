package com.example.carousels

class Repository(private val api: Api) : BaseRepository() {
    suspend fun getConfig(url:String): List<Carousel>? {
        return safeApiCall(
            call = { api.getConfigAsync(url).await() },
            errorMessage = "Error Fetching Popular Movies"
        )
    }
}
