package com.example.carousels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
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
    private val disposables = CompositeDisposable()


    fun getUrl(id: String) {
        scope.launch {
            val link = repository.getShow(id.toInt())
            link?.let {
                url.postValue(it)

            }
        }

    }

    fun updateTime(playback: Observable<Pair<Int, Long>>) {
        playback.subscribe { (videoId, time) ->
            scope.launch {
                val response = repository.saveCurrentTime(videoId, time / 1000)
                println(response)
            }
        }.addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}