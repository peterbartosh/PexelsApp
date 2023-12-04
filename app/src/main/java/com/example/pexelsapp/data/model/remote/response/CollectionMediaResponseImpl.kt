package com.example.pexelsapp.data.model.remote.response

import com.example.pexelsapp.data.model.remote.primary.NetworkPhoto
import com.google.gson.annotations.SerializedName

data class CollectionMediaResponseImpl(
    val media: List<NetworkPhoto>,
    val id: String,
    val page: Int,
    @SerializedName("next_page")
    val nextPage: String,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("prev_page")
    val prevPage: String,
    @SerializedName("total_results")
    val totalResults: Int
): ApiResponse