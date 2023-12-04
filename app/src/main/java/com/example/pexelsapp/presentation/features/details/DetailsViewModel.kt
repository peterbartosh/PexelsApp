package com.example.pexelsapp.presentation.features.details

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.pexelsapp.R as Res
import com.example.pexelsapp.data.repository.PexelsApiRepository
import com.example.pexelsapp.data.repository.RoomRepository
import com.example.pexelsapp.data.utils.ErrorType
import com.example.pexelsapp.data.utils.UiState
import com.example.pexelsapp.data.utils.saveToStorage
import com.example.pexelsapp.data.utils.showToast
import com.example.pexelsapp.data.remote.Callbacks
import com.example.pexelsapp.domain.Photo
import com.example.pexelsapp.domain.toPhoto
import com.example.pexelsapp.domain.toPhotoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val pexelsApiRepository: PexelsApiRepository,
    private val roomRepository: RoomRepository,
    private val callbacks: Callbacks
) : ViewModel(){

    private val _uiState = MutableStateFlow<UiState<Photo>>(UiState.Loading())
    val uiState: StateFlow<UiState<Photo>> = _uiState

    private var bitmap: Bitmap? = null

    var downloadingState by mutableStateOf(false)

    var isInBookmarks by mutableStateOf(false)

    fun getPhoto(photoId: Int?, isNetworkRequest: Boolean) = viewModelScope.launch {

        _uiState.value = UiState.Loading()

        if (photoId != null) {
            try {

                val photo = withContext(Dispatchers.Default) {
                    if (isNetworkRequest) {
                        isInBookmarks = roomRepository.isInBookmarks(photoId)
                        pexelsApiRepository.getPhoto(photoId).getOrThrow()?.toPhoto()
                    }
                    else {
                        isInBookmarks = true
                        roomRepository.getPhotoById(photoId).toPhoto()
                    }
                }

                _uiState.value = UiState.Success(photo)


            } catch (hE: HttpException){
                _uiState.value = UiState.Failure(ErrorType.HttpError)
                callbacks.httpErrorCallback(hE.code())
            } catch (ioE: IOException){
                _uiState.value = UiState.Failure(ErrorType.ConnectionLost)
                callbacks.connectionLostErrorCallback()
            }
        } else {
            _uiState.value = UiState.Failure(ErrorType.EmptyResult)
        }
    }

    fun notifyState(isSuccess: Boolean, bmp: Bitmap? = null) {
        if (!isSuccess)
            _uiState.value = UiState.Failure(ErrorType.EmptyResult)
        if (bmp != null)
            bitmap = bmp
    }

    fun savePhotoToGallery(context: Context) = viewModelScope.launch {
        downloadingState = true
        val errorMessageInd = Res.string.download_failed
        val successMessageInd = Res.string.download_successful
        val messageId = withContext(Dispatchers.IO) {
            _uiState.value.data?.let { photo ->
                bitmap?.let { bmp ->
                    val isSuccess = bmp.saveToStorage(context, photo)
                    if (isSuccess) successMessageInd else errorMessageInd
                } ?: errorMessageInd
            } ?: errorMessageInd
        }
        context.showToast(context.getString(messageId))
        downloadingState = false
    }

    fun addPhotoToBookmarks() = viewModelScope.launch {
        _uiState.value.data?.toPhotoEntity()?.let { photoEntity ->
            roomRepository.addPhoto(photoEntity)
            isInBookmarks = true
        }
    }

    fun removePhotoFromBookmarks() = viewModelScope.launch {
        _uiState.value.data?.id?.let { id ->
            roomRepository.deletePhoto(id)
            isInBookmarks = false
        }
    }

}