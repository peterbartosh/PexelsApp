package com.example.pexelsapp.data.model.remote.response

import com.example.pexelsapp.data.model.remote.primary.NetworkPhoto
import com.google.gson.annotations.SerializedName

data class SearchResponseImpl(
    val photos: List<NetworkPhoto>,
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("next_page")
    val nextPage: String
): ApiResponse