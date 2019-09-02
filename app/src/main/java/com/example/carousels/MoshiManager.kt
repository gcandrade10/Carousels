package com.example.carousels

import com.squareup.moshi.Moshi

object MoshiManager {

    private fun init(): Moshi? {
        return Moshi.Builder().build()
    }

    val moshi by lazy { init() }
}