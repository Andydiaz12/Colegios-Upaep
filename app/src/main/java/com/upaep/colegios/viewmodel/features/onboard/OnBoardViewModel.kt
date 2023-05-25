package com.upaep.colegios.viewmodel.features.onboard

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.upaep.colegios.R
import com.upaep.colegios.data.entities.onboard.OnBoardInfo
import com.upaep.colegios.view.base.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor() : ViewModel() {
    private val name = "Juan López"
    private val _actualPage = MutableLiveData(0)
    private val _leftScreen = MutableLiveData<Int>()
    private val _scrollDirectionLeft = MutableLiveData(true)
    val scrollDirectionLeft: LiveData<Boolean> = _scrollDirectionLeft
    val actualPage: LiveData<Int> = _actualPage

    fun changeOnBoardingScreen(value: Int, navigation: NavHostController) {
        if(_actualPage.value == 2 && value > 0) return navigateToStudentSelector(navigation)
        if ((value > 0 && _actualPage.value == 2) || (value < 0 && _actualPage.value == 0)) return
        _scrollDirectionLeft.value = value == -1
        _actualPage.value = _actualPage.value!!.plus(value)
    }

    fun tapOnBoardingScreen(tapX: Float, navigation: NavHostController) {
        changeOnBoardingScreen(value = if (tapX >= _leftScreen.value!!) 1 else -1, navigation = navigation)
    }

    fun setMiddleofComponent(value: Int) {
        _leftScreen.value = value
    }

    fun navigateToStudentSelector(navigation: NavHostController) {
        navigation.navigate(Routes.StudentSelectorScreen.routes)
    }

    fun getScreensInfo(): List<OnBoardInfo> {
        return listOf(
            OnBoardInfo(
                title = "Hola ${name},",
                text = "te damos la bienvenida a la\naplicación para padres de familia",
                image = R.drawable.icono_brumildo_onboarding,
                color = Color(0xFF3E9605).copy(alpha = 0.75f)
            ),
            OnBoardInfo(
                text = "Consulta las calificaciones, horario y mantente informado de todo lo que pasa en Educación básica UPAEP",
                image = R.drawable.icono_onboarding_2,
                color = Color(0xFFD24E00).copy(alpha = 0.8f)
            ),
            OnBoardInfo(
                text = "Paga en línea y consulta\nlas colegiaturas",
                image = R.drawable.icono_onboarding_3,
                color = Color(0xFFD99700).copy(alpha = 0.75f)
            )
        )
    }
}