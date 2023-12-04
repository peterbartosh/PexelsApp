package com.example.pexelsapp.data.repository

import android.util.Log
import com.example.pexelsapp.data.model.remote.response.ApiResponse
import com.example.pexelsapp.data.model.remote.response.CuratedResponseImpl
import com.example.pexelsapp.data.remote.PexelsApi
import com.example.pexelsapp.data.utils.MediaType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException
import java.util.Locale
import javax.inject.Inject

const val TAG = "ERROR_API"

class PexelsApiRepository @Inject constructor(private val pexelsApi: PexelsApi) {

    suspend fun getFeaturedCollections(page: Int) = safeInvoke {
        pexelsApi.getFeaturedCollections(page = page)
    }

    suspend fun getCuratedPhotos(page: Int) = safeInvoke {
        pexelsApi.getCuratedPhotos(page = page)
    }

    suspend fun getMediasByCollectionId(collectionId: String, page: Int, type: MediaType = MediaType.Photos) = safeInvoke {
        pexelsApi.getMediasByCollectionId(collectionId = collectionId, page = page, type = type.name.lowercase())
    }

    suspend fun searchPhotos(query: String, page: Int, localeTag: String) = safeInvoke {
        pexelsApi.searchPhotos(query = query, page = page, localeTag = localeTag)
    }

    suspend fun getPhoto(photoId: Int) = safeInvoke {
        pexelsApi.getPhoto(photoId = photoId)
    }

    private suspend fun <T: ApiResponse> safeInvoke(func: suspend () -> Response<T>) =
        withContext(Dispatchers.IO) {
            try {
                val response = func.invoke()
                if (response.isSuccessful) {
                    Result.success(response.body())
                } else {
                    Log.d(TAG, response.errorBody()?.string().toString())
                    Result.failure(HttpException(response))
                }
            } catch (ioE: IOException){
                Log.d(TAG, ioE.toString())
                Result.failure(ioE)
            }
        }

}