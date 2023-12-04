package com.example.pexelsapp.presentation.features.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsapp.data.repository.PexelsApiRepository
import com.example.pexelsapp.data.utils.ErrorType
import com.example.pexelsapp.data.utils.NetworkRequestType
import com.example.pexelsapp.data.utils.UiState
import com.example.pexelsapp.data.remote.Callbacks
import com.example.pexelsapp.domain.Collection
import com.example.pexelsapp.domain.Photo
import com.example.pexelsapp.domain.toCollection
import com.example.pexelsapp.domain.toPhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pexelsApiRepository: PexelsApiRepository,
    private val callbacks: Callbacks
) : ViewModel(){

    private var photosPage = 1
    private var collectionsPage = 1
    private var currentQuery = ""
    private var currentCollectionId = ""
    private var currentNetworkRequestType = NetworkRequestType.Curated

    private val _uiState = MutableStateFlow<UiState<Boolean>>(UiState.NotStarted())
    val uiState: StateFlow<UiState<Boolean>> = _uiState

    val photosState = SnapshotStateList<Photo>()
    var collections = SnapshotStateList<Collection>()

    var lastPickedCollectionIndex by mutableStateOf(-1)

    init {
        getFeaturedCollections()
        getCuratedPhotos()
    }

    fun getCuratedPhotos() = viewModelScope.launch {
        launch {
            _uiState.value = UiState.Loading()
            photosPage = 1
            currentNetworkRequestType = NetworkRequestType.Curated
            photosState.clear()
        }.join()
        uploadMorePhotos()
    }

    fun searchPhotos(query: String, localeTag: String) = viewModelScope.launch {
        launch {
            _uiState.value = UiState.Loading()
            photosPage = 1
            currentQuery = query
            currentNetworkRequestType = NetworkRequestType.Search
            photosState.clear()
        }
        uploadMorePhotos(localeTag)
    }

    fun getCollectionPhotos(collectionId: String) = viewModelScope.launch {
        launch {
            _uiState.value = UiState.Loading()
            photosPage = 1
            currentCollectionId = collectionId
            currentNetworkRequestType = NetworkRequestType.Collection
            photosState.clear()
        }
        uploadMorePhotos()
    }

    fun refresh() = viewModelScope.launch {
        launch {
            _uiState.value = UiState.Loading()
            photosPage = 1
            photosState.clear()
        }
        if (collections.isEmpty())
            getFeaturedCollections()
        uploadMorePhotos()
    }

    fun uploadMorePhotos(localeTag: String = "en-US") = viewModelScope.launch {

        try {

            val photos = when (currentNetworkRequestType) {
                NetworkRequestType.Curated ->
                    pexelsApiRepository.getCuratedPhotos(photosPage).getOrThrow()?.photos

                NetworkRequestType.Search ->
                    pexelsApiRepository.searchPhotos(currentQuery, photosPage, localeTag).getOrThrow()?.photos

                NetworkRequestType.Collection ->
                    pexelsApiRepository.getMediasByCollectionId(currentCollectionId, photosPage)
                        .getOrThrow()?.media
            }

            photosPage++

            if (photos != null)
                photosState.addAll(photos.map { it.toPhoto() })

            _uiState.value = if (photos?.isNotEmpty() == true)
                UiState.Success()
            else
                UiState.Failure(ErrorType.EmptyResult)

        } catch (hE: HttpException){
            _uiState.value = UiState.Failure(ErrorType.HttpError)
            callbacks.httpErrorCallback(hE.code())
        } catch (ioE: IOException){
            _uiState.value = if (photosPage == 1)
                UiState.Failure(ErrorType.ConnectionLost)
            else
                UiState.Success()

            callbacks.connectionLostErrorCallback()
        }
    }

    private fun getFeaturedCollections() = viewModelScope.launch {
        collectionsPage = 1
        uploadMoreFeaturesCollections()
    }

    fun uploadMoreFeaturesCollections() = viewModelScope.launch {
        val result = pexelsApiRepository.getFeaturedCollections(collectionsPage)
        try {
            collections.addAll(
                result.getOrThrow()?.collections?.map { it.toCollection() } ?: emptyList()
            )
            collectionsPage++
        } catch (hE: HttpException){
            callbacks.httpErrorCallback(hE.code())
        } catch (ioE: IOException){
            callbacks.connectionLostErrorCallback()
        }
    }

    fun pickCollection(collection: Collection) = viewModelScope.launch {
        if (lastPickedCollectionIndex != -1)
            unpickCollection().join()
        val curIndex = collections.indexOf(collection)
        lastPickedCollectionIndex = curIndex
        collections.removeAt(curIndex)
        collections.add(0, collection)
    }

    fun unpickCollection() = viewModelScope.launch {
        val collection = collections[0]
        collections.removeAt(0)
        collections.add(lastPickedCollectionIndex, collection)
        lastPickedCollectionIndex = -1
    }

}