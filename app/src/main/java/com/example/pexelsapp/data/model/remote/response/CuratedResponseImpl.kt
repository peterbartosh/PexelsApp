package com.example.pexelsapp.data.model.remote.response

import com.example.pexelsapp.data.model.remote.primary.NetworkPhoto
import com.google.gson.annotations.SerializedName

data class CuratedResponseImpl(
    val photos: List<NetworkPhoto>,
    val page: Int,
    @SerializedName("next_page")
    val nextPage: String,
    @SerializedName("per_page")
    val perPage: Int
): ApiResponse