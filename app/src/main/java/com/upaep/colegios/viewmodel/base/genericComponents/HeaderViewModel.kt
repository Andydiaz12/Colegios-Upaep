package com.upaep.colegios.viewmodel.base.genericComponents

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HeaderViewModel @Inject constructor() : ViewModel() {

    //CREAR EL FLOW DE INFORMACIÓN DE COLOR DE HEADER AQUÍ PARA NO AGREGAR EN CADA COMPONENTE

    fun changeSelectedStudent() {

    }

}