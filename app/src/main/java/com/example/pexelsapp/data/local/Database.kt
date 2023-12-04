package com.example.pexelsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pexelsapp.data.model.local.PhotoEntity

@Database(
    entities = [PhotoEntity::class],
    version = 2,
    exportSchema = false
)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun getDao(): LocalDao
}