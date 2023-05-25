package com.upaep.colegios.viewmodel.features.grades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.colegios.data.domain.grades.GetGradesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GradesViewModel @Inject constructor(private val getGradesUseCase: GetGradesUseCase) : ViewModel() {
    init {
        viewModelScope.launch {
            getGradesUseCase()
        }
    }
}