package com.example.pexelsapp.data.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.pexelsapp.R
import com.example.pexelsapp.domain.Photo
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@Composable
fun Context.getWidthPercent(): Dp {
    val displayMetrics = this.resources.displayMetrics
    return ((displayMetrics.widthPixels / displayMetrics.density) / 100).dp
}

@Composable
fun Context.getHeightPercent(): Dp {
    val displayMetrics = this.resources.displayMetrics
    return ((displayMetrics.heightPixels / displayMetrics.density) / 100).dp
}

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }

fun Context.isUserConnected() : Boolean {
    val hasInternet: Boolean

    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        hasInternet = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        hasInternet = try {
            if (connectivityManager.activeNetworkInfo == null) {
                false
            } else {
                connectivityManager.activeNetworkInfo?.isConnected!!
            }
        } catch (e: Exception) {
            false
        }
    }
    return hasInternet
}

fun Bitmap.saveToStorage(context: Context, photo: Photo): Boolean {
    val filename = photo.alt + ".png"

    var fos: OutputStream? = null

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context.contentResolver?.also { resolver ->

            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }

            val imageUri: Uri? =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            fos = imageUri?.let { resolver.openOutputStream(it) }
        }
    } else {
        val imagesDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File(imagesDir, filename)
        fos = FileOutputStream(image)
    }

    return fos?.use { stream ->
        this.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        true
    } ?: false
}

fun convertHttpErrorCodeToMessage(code: Int, context: Context) =
    when (code){
        401 -> context.getString(R.string.forbidden)
        429 -> context.getString(R.string.too_many_requests)
        else -> context.getString(R.string.server_error)
}

