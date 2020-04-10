package com.example.carousels

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.carousels.CarouselsApplication.Companion.context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import kotlinx.android.synthetic.main.activity_player.*
import okhttp3.Cookie
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
        window.decorView.apply {
            systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )

//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
//        actionBar?.hide()
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        );
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

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

    private fun play(source: String, position: Long, videoId: Int) {

        val json = context.getSharedPreferences("mysharedpref", Context.MODE_PRIVATE)
            .getString("PREF_COOKIES", "")
        val gson = Gson()
        val cookies: MutableList<Cookie> = gson.fromJson(
            json,
            object :
                TypeToken<MutableList<Cookie?>?>() {}.type
        )
        val cookieList = cookies.map {
            "${it.name()}=${it.value()}"
        }.reduce { acc, s -> "$acc;$s" }

        var map: HashMap<String, String> = HashMap()
        map["Connection"] = "keep-alive"
        map["profileId"] = "1"
        map["Accept"] = "application/json, text/plain, */*"
        map["X-Requested-With"] = "XMLHttpRequest"
        map["X-Requested-With"] = "XMLHttpRequest"
        map["X-Requested-With"] = "XMLHttpRequest"
        cookieList?.let {
            map["Cookie"] = it
        }
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
        val playback =
            Observable.interval(5, TimeUnit.SECONDS).map { videoId to player.currentPosition }
        playback.observeOn(AndroidSchedulers.mainThread()).subscribe {
            window.decorView.apply {
                systemUiVisibility =
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
            }
        }
        model.updateTime(playback)
//        player.createMessage { messageType, payload -> model.saveTime(player.) }


    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()

    }
}
