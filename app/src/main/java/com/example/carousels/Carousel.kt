package com.example.carousels


data class Carousel(val title: String, val type: String, val items: List<Item>)
data class Item(val title: String, val url: String, val video: String)
