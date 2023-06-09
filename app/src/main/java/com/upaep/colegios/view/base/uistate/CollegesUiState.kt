package com.upaep.colegios.view.base.uistate

sealed interface CollegesUiState {
    object Loading : CollegesUiState
    data class Error(val throwable: Throwable) : CollegesUiState
    data class Success<T>(val data: T) : CollegesUiState
//    data class Success(val students: List<StudentsSelector>) : CollegesUiState
}