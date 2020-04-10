package com.example.carousels

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.carousels.CarouselsApplication.Companion.context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_player.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit


class Player : AppCompatActivity() {
    companion object {
        fun getIntent(context: Context, url: String): Intent {
            val intent = Intent(context, Player::class.java)
            intent.putExtra("video", url)
            return intent
        }
    }

    private val model: PlayerModel by viewModel()
    private lateinit var player: ExoPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val id = intent.getStringExtra("video")

        model.url.observe(this, Observer { show ->
            play(
                "http://192.168.0.16:8080${show.files.first().src}",
                show.viewedStatus.currentPlayTime.toLong() * 1000,
                show.id
            )

        })
        model.getUrl(id)

    }

    private fun play(source: String, position: Long, videoId:Int) {
        var map: HashMap<String, String> = HashMap()
        map["Connection"] = "keep-alive"
        map["profileId"] = "1"
        map["Accept"] = "application/json, text/plain, */*"
        map["X-Requested-With"] = "XMLHttpRequest"
        map["X-Requested-With"] = "XMLHttpRequest"
        map["X-Requested-With"] = "XMLHttpRequest"
        map["Cookie"] = cookie
        player = SimpleExoPlayer.Builder(context).build()
        playerView.player = player
        //andExoPlayerView.setSource(source, map)

        val uri = Uri.parse(source)

        val sourceFactory = DefaultHttpDataSourceFactory("exoplayer-agent")
        if (map != null) {
            for (entry in map.entries)
                sourceFactory.defaultRequestProperties.set(entry.key, entry.value)
        }

        val mediaSource = ProgressiveMediaSource.Factory(sourceFactory).createMediaSource(uri)

        player.prepare(mediaSource, true, false)
        player.seekTo(position)
        player.playWhenReady = true
        val playback = Observable.interval(5, TimeUnit.SECONDS).map { videoId to player.currentPosition }
        model.updateTime(playback)
//        player.createMessage { messageType, payload -> model.saveTime(player.) }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()

    }
}
