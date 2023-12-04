package com.example.pexelsapp.data.remote

import android.content.Context
import android.util.Log
import com.example.pexelsapp.data.utils.CACHING_DURATION
import com.example.pexelsapp.data.utils.isUserConnected
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class CachingInterceptor @Inject constructor(@ApplicationContext private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = if (context.isUserConnected())
            request.newBuilder().header("Cache-Control", "public, max-age=5").build()
        else
            request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$CACHING_DURATION")
                .build()

        return chain.proceed(request)
    }
}