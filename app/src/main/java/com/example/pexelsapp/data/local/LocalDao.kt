package com.example.pexelsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pexelsapp.data.model.local.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDao {
    @Query("Select * From photos")
    fun getPhotos(): Flow<List<PhotoEntity>>

    @Query("Select * From photos")
    suspend fun getPhotosAsList(): List<PhotoEntity>

    @Query("Select * From photos Where id = :photoId")
    suspend fun getPhotoById(photoId: Int): PhotoEntity

    @Query("Select Count(*) From photos Where id = :photoId")
    suspend fun findPhoto(photoId: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photoEntity: PhotoEntity)

    @Query("Delete From photos Where id = :photoId")
    suspend fun deletePhoto(photoId: Int)

    @Query("Delete From photos")
    suspend fun clearAll()
}