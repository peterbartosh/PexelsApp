package com.example.pexelsapp.data.model.remote.secondary

import com.google.gson.annotations.SerializedName

data class VideoFile(
    @SerializedName("file_type")
    val fileType: String,
    val height: Int,
    val id: Int,
    val link: String,
    val quality: String,
    val width: Int
)