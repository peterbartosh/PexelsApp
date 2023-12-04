package com.example.pexelsapp.data.remote

import com.example.pexelsapp.PEXELS_API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", PEXELS_API_KEY)
            .build()

        return chain.proceed(request)
    }
}