package com.example.pexelsapp.domain

import com.example.pexelsapp.data.model.local.PhotoEntity
import com.example.pexelsapp.data.model.remote.primary.NetworkCollection
import com.example.pexelsapp.data.model.remote.primary.NetworkPhoto

// photos

fun PhotoEntity.toPhoto() = Photo(
    id = id,
    alt = alt,
    url = url,
    width = width,
    height = height,
    authorName = author
)

fun Photo.toPhotoEntity() = PhotoEntity(
    id = id,
    alt = alt,
    url = url,
    width = width,
    height = height,
    author = authorName
)

fun NetworkPhoto.toPhoto() = Photo(
    id = id,
    alt = alt,
    url = src.original,
    width = width,
    height = height,
    authorName = photographer
)

// collections

fun NetworkCollection.toCollection() = Collection(
    id = id,
    title = title
)

