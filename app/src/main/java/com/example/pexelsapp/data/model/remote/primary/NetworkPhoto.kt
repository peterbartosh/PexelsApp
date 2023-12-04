package com.example.pexelsapp.data.model.remote.primary

import com.example.pexelsapp.data.model.remote.response.ApiResponse
import com.example.pexelsapp.data.model.remote.secondary.Src
import com.google.gson.annotations.SerializedName

data class NetworkPhoto(
    val alt: String,
    @SerializedName("avg_color")
    val avg_color: String,
    val height: Int,
    val id: Int,
    val liked: Boolean,
    val photographer: String,
    @SerializedName("photographer_id")
    val photographerId: Int,
    @SerializedName("photographer_url")
    val photographerUrl: String,
    val src: Src,
    val url: String,
    val width: Int
): ApiResponse