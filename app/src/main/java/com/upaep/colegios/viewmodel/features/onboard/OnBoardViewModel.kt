package com.upaep.colegios.viewmodel.features.onboard

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.upaep.colegios.data.entities.onboard.OnBoardInfo
import com.upaep.colegios.view.base.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor() : ViewModel() {

    private val name = "Juan López"
    val screensContent = getScreensInfo()

    private val _actualScreen = MutableLiveData(1)
    val actualScreen: LiveData<Int> = _actualScreen

    private val _constraintWidth = MutableLiveData<Float>()
    private val _rightSwipe = MutableLiveData<Boolean>()

    fun tappingEvent(tapOnX: Float, navigation: NavHostController) {
        _rightSwipe.value = tapOnX > (_constraintWidth.value!! / 2)
        navigateOnBoard(navigation = navigation)
    }

    fun navigateOnBoard(navigation: NavHostController) {
        if (_rightSwipe.value!!) {
            if (_actualScreen.value == 3) {
                navigateToStudentSelector(navigation = navigation)
            } else _actualScreen.value = _actualScreen.value!! + 1
        } else {
            if (_actualScreen.value!! > 1) _actualScreen.value = _actualScreen.value!! - 1
        }
    }

    fun navigateToStudentSelector(navigation: NavHostController) {
        navigation.navigate(Routes.StudentSelectorScreen.routes)
    }

    fun updateConstraintWidth(width: Float) {
        _constraintWidth.value = width
    }

    fun updateRightSwipe(isRight: Boolean) {
        _rightSwipe.value = isRight
    }

    fun getScreensInfo(): List<OnBoardInfo> {
        return listOf(
            OnBoardInfo(
                title = "Hola ${name},",
                text = "te damos la bienvenida a la\naplicación para padres de familia"
            ),
            OnBoardInfo(
                text = "Consulta las calificaciones, horario y mantente informado de todo lo que pasa en Educación básica UPAEP"
            ),
            OnBoardInfo(
                text = "Paga en línea y consulta las colegiaturas"
            )
        )
    }
}