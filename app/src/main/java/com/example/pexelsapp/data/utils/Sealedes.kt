package com.example.pexelsapp.data.utils

sealed class UiState<T>(open val data: T? = null, open val errorType: ErrorType? = null){
    class Success<T>(override val data: T? = null): UiState<T>(data = data)
    class Failure<T>(override val errorType: ErrorType): UiState<T>(errorType = errorType)
    class Loading<T> : UiState<T>()
    class NotStarted<T> : UiState<T>()
}