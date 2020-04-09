package com.example.carousels

class Show(
    val id: String,
    val overview: String,
    val files: List<File>,
    val viewedStatus: ViewedStatus
)

class File(val id: Int, val src: String)

class ViewedStatus(val currentPlayTime: Int)