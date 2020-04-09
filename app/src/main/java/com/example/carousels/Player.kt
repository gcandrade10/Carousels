package com.example.carousels

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_player.*
import org.koin.android.viewmodel.ext.android.viewModel
import com.google.android.exoplayer2.SimpleExoPlayer
import android.net.Uri
import com.example.carousels.CarouselsApplication.Companion.context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory


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
                show.viewedStatus.currentPlayTime.toLong() * 1000
            )

        })
        model.getUrl(id)

    }

    private fun play(source: String, position: Long) {
        var map: HashMap<String, String> = HashMap()
        map["Connection"] = "keep-alive"
        map["profileId"] = "1"
        map["Accept"] = "application/json, text/plain, */*"
        map["X-Requested-With"] = "XMLHttpRequest"
        map["X-Requested-With"] = "XMLHttpRequest"
        map["X-Requested-With"] = "XMLHttpRequest"
        map["Cookie"] =
            "streama_remember_me=YWRtaW46MTU4NzY1MTA4NzEwMzplMDc2NTg0NjE0ZjVmNzA5Y2IyZjU4YjNmYzQ0YzQ3YQ; JSESSIONID=77F74E8FBED35433314F211583D8A42F"

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
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}
