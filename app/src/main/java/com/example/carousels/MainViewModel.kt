package com.example.carousels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    val mainLiveData = MutableLiveData<List<Carousel>>()
    val urlLiveData = MutableLiveData<String>()


    init {
        urlLiveData.postValue(url)
        getConfig(url)
    }

    fun getConfig(pUrl: String) {
        scope.launch {
            val config = repository.getConfig(pUrl)
            val watching = repository.getlistContinueWatching()

            watching?.let {list ->
                val items = list.map {
                    Item(it.video.title, "https://image.tmdb.org/t/p/w300${it.video.poster_path}", "${it.id}")
                }
                val carousel = Carousel("Continue watching", "thumb", items)
                mainLiveData.postValue(listOf(carousel))
            }
        }
    }

}
