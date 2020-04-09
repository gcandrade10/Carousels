package com.example.carousels

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_player.*
import org.koin.android.viewmodel.ext.android.viewModel


class Player : AppCompatActivity() {
    companion object {
        fun getIntent(context: Context, url: String): Intent {
            val intent = Intent(context, Player::class.java)
            intent.putExtra("video", url)
            return intent
        }
    }

    private val model: PlayerModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val id = intent.getStringExtra("video")

        model.url.observe(this, Observer { url ->
            play("http://192.168.0.16:8080$url")

        })
        model.getUrl(id)

    }

    fun play(source: String) {
        var map: HashMap<String, String> = HashMap<String, String>()
        map["Connection"] = "keep-alive"
        map["profileId"] = "1"
        map["Accept"] = "application/json, text/plain, */*"
        map["X-Requested-With"] = "XMLHttpRequest"
        map["X-Requested-With"] = "XMLHttpRequest"
        map["X-Requested-With"] = "XMLHttpRequest"
        map["Cookie"] =
            "JSESSIONID=20F894AE42557C6E22B69E5CF485091E; streama_remember_me=YWRtaW46MTU4NzY1MTA4NzEwMzplMDc2NTg0NjE0ZjVmNzA5Y2IyZjU4YjNmYzQ0YzQ3YQ"
        andExoPlayerView.setSource(source, map)

    }
}
