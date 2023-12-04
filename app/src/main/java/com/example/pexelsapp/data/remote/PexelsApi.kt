package com.example.pexelsapp.data.remote

import com.example.pexelsapp.data.model.remote.primary.NetworkPhoto
import com.example.pexelsapp.data.model.remote.response.CollectionMediaResponseImpl
import com.example.pexelsapp.data.model.remote.response.CuratedResponseImpl
import com.example.pexelsapp.data.model.remote.response.FeaturedCollectionsResponse
import com.example.pexelsapp.data.model.remote.response.SearchResponseImpl
import com.example.pexelsapp.data.utils.CACHING_DURATION
import com.example.pexelsapp.data.utils.FEATURED_COLLECTIONS_PER_PAGE
import com.example.pexelsapp.data.utils.PHOTOS_PER_PAGE
import okhttp3.CacheControl
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface PexelsApi {

    @GET("v1/collections/featured")
    suspend fun getFeaturedCollections(
        @Header("Cache-Control") cacheControl: String = "max-age=$CACHING_DURATION",
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = FEATURED_COLLECTIONS_PER_PAGE,
    ): Response<FeaturedCollectionsResponse>

    @GET("v1/curated")
    suspend fun getCuratedPhotos(
        @Header("Cache-Control") cacheControl: String = "max-age=$CACHING_DURATION",
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = PHOTOS_PER_PAGE,
    ): Response<CuratedResponseImpl>

    @GET("v1/collections/{collectionId}")
    suspend fun getMediasByCollectionId(
        @Header("Cache-Control") cacheControl: String? = null,
        @Path("collectionId") collectionId: String,
        @Query("page") page: Int,
        @Query("type") type: String = "photos",
        @Query("per_page") perPage: Int = PHOTOS_PER_PAGE,
    ): Response<CollectionMediaResponseImpl>

    @GET("v1/search")
    suspend fun searchPhotos(
        @Header("Cache-Control") cacheControl: String? = null,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = PHOTOS_PER_PAGE,
        @Query("locale") localeTag: String
    ): Response<SearchResponseImpl>

    @GET("v1/photos/{photoId}")
    suspend fun getPhoto(
        @Header("Cache-Control") cacheControl: String? = null,
        @Path("photoId") photoId: Int
    ): Response<NetworkPhoto>
}