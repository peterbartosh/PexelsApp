package com.example.pexelsapp.data.model.remote.response

import com.example.pexelsapp.data.model.remote.primary.NetworkCollection
import com.google.gson.annotations.SerializedName

data class FeaturedCollectionsResponse(
    val collections: List<NetworkCollection>,
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