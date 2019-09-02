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
            mainLiveData.postValue(config)
        }
    }

}
