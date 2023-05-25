package com.upaep.colegios.data.entities

import com.upaep.colegios.R
import com.upaep.colegios.data.api.APIStatusCode

sealed class AnswerBack<T> {
    data class Success<T>(val data: T) : AnswerBack<T>()
    data class AccessDenied<T>(val data: T) : AnswerBack<T>()
    data class InternalError<T>(val errorMessage: String) : AnswerBack<T>()
    data class Error<T>(val errorMessage: String) : AnswerBack<T>()
    data class NetworkError<T>(val errorMessage: String = "Error al conectarse a internet") : AnswerBack<T>()
}