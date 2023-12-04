package com.example.pexelsapp.data.repository

import com.example.pexelsapp.data.local.LocalDao
import com.example.pexelsapp.data.model.local.PhotoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomRepository @Inject constructor(private val localDao: LocalDao) {

    fun getPhotos() = localDao.getPhotos().flowOn(Dispatchers.IO)

    suspend fun getPhotosAsList() = withContext(Dispatchers.IO){
        localDao.getPhotosAsList()
    }

    suspend fun getPhotoById(photoId: Int) = withContext(Dispatchers.IO){
        localDao.getPhotoById(photoId)
    }

    suspend fun isInBookmarks(photoId: Int) = withContext(Dispatchers.IO){
        localDao.findPhoto(photoId) != 0
    }

    suspend fun addPhoto(photoEntity: PhotoEntity) = withContext(Dispatchers.IO){
        localDao.insertPhoto(photoEntity)
    }

    suspend fun deletePhoto(photoId: Int) = withContext(Dispatchers.IO){
        localDao.deletePhoto(photoId)
    }

    suspend fun clearAll() = withContext(Dispatchers.IO){
        localDao.clearAll()
    }

}