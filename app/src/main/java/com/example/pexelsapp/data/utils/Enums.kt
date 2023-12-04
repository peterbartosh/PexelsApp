package com.example.pexelsapp.data.utils

enum class MediaType{
    Photos, Videos
}

enum class NetworkRequestType{
    Curated, Search, Collection
}

enum class ErrorType(val message: String? = null){
    ConnectionLost, HttpError, EmptyResult;
}