package com.example.pexelsapp.presentation.features.bookmarks

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsapp.data.repository.RoomRepository
import com.example.pexelsapp.data.utils.ErrorType
import com.example.pexelsapp.data.utils.UiState
import com.example.pexelsapp.domain.Photo
import com.example.pexelsapp.domain.toPhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(private val roomRepository: RoomRepository) : ViewModel(){

    private val _uiState = MutableStateFlow<UiState<List<Photo>>>(UiState.NotStarted())
    val uiState: StateFlow<UiState<List<Photo>>> = _uiState

    init {
        viewModelScope.launch {
            roomRepository.getPhotos()
                .catch { e ->
                    Log.d("ERROR_ERROR", "init: $e")
                    _uiState.value = UiState.Failure(ErrorType.EmptyResult)
                }
                .collect { productEntities ->
                    _uiState.value = if (productEntities.isNotEmpty())
                        UiState.Success(productEntities.map { it.toPhoto() })
                    else
                        UiState.Failure(ErrorType.EmptyResult)
                }
        }
    }
}