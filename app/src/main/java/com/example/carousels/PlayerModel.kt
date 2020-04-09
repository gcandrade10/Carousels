package com.example.carousels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PlayerModel(private val repository: Repository) : ViewModel() {
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    val url = MutableLiveData<Show>()

    fun getUrl(id: String) {
        scope.launch {
            val link = repository.getShow(id.toInt())
            link?.let {
                url.postValue(it)

            }
        }

    }


}