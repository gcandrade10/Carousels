package com.example.carousels

class Repository(private val api: Api) : BaseRepository() {
    suspend fun getConfig(url: String): List<Carousel>? {
        return safeApiCall(
            call = { api.getConfigAsync(url).await() },
            errorMessage = "Error Fetching Popular Movies"
        )
    }

    suspend fun getlistContinueWatching(): List<MyTitle>? {
        return safeApiCall(
            call = { api.getlistContinueWatching().await() },
            errorMessage = "Error Fetching Popular Movies"
        )
    }

    suspend fun getShow(id: Int): Show? {
        return safeApiCall(
            call = { api.getShow(id).await() },
            errorMessage = "Error Fetching Popular Movies"
        )
    }

    suspend fun saveCurrentTime(videoId: Int, time: Long): SaveResponse? {
        return safeApiCall(
            call = { api.saveCurrentTime(videoId, time).await() },
            errorMessage = "Error saving Current Time"
        )
    }
}
