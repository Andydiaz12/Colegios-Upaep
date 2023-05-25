package com.upaep.colegios.viewmodel.features.home

import android.app.Application
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.colegios.data.base.preferences.UserPreferences
import com.upaep.colegios.data.domain.home.GetFeaturesUseCase
import com.upaep.colegios.data.entities.studentselector.StudentsSelector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val application: Application
    //private val getFeatures: GetFeaturesUseCase
) : ViewModel() {
    private val userPreferences = UserPreferences.getInstance(application.applicationContext)
    val levelColor : LiveData<Color> = MutableLiveData(Color(userPreferences.getColor()))
    val studentData : LiveData<StudentsSelector> = MutableLiveData(userPreferences.getChildSelectedData())

//    init {
//        viewModelScope.launch {
//            Log.i("featuresService", getFeatures().toString())
//            //getFeatures()
//        }
//    }
}