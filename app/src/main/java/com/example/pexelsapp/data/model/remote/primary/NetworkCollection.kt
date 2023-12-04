package com.example.pexelsapp.data.model.remote.primary

import com.google.gson.annotations.SerializedName

data class NetworkCollection(
    @SerializedName("media_count")
    val mediaCount: Int,
    @SerializedName("photos_count")
    val photosCount: Int,
    val description: String,
    val id: String,
    val private: Boolean,
    val title: String,
    val videos_count: Int
)