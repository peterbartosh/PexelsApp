package com.example.pexelsapp.data.remote

import android.content.Context
import com.example.pexelsapp.R
import com.example.pexelsapp.data.utils.convertHttpErrorCodeToMessage
import com.example.pexelsapp.data.utils.showToast
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class Callbacks @Inject constructor(
    private val context: Context
) {
    suspend fun httpErrorCallback(code: Int){
        withContext(Dispatchers.Main) {
            val message = convertHttpErrorCodeToMessage(code, context)
            context.showToast(message)
        }
    }

    suspend fun connectionLostErrorCallback() {
        withContext(Dispatchers.Main) {
            context.showToast(context.getString(R.string.connection_lost))
        }
    }
}