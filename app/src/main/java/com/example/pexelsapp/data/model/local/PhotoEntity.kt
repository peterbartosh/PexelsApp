package com.example.pexelsapp.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey
    val id: Int,
    val alt: String,
    val url: String,
    val width: Int,
    val height: Int,
    val author: String
)