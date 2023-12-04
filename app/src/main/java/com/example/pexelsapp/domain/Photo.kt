package com.example.pexelsapp.domain

data class Photo(
    val id: Int = 0,
    val alt: String,
    val width: Int,
    val height: Int,
    val url: String,
    val authorName: String
)

