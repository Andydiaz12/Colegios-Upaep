package com.upaep.colegios.viewmodel.features.payments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.upaep.colegios.model.entities.payments.CompanyName
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaxDataViewModel @Inject constructor() : ViewModel() {

    private val _companyNames: MutableLiveData<List<CompanyName>> = MutableLiveData(getCompanyNames())
    val companyNames : LiveData<List<CompanyName>> = _companyNames

    fun getCompanyNames(): List<CompanyName> {
        return listOf(
            CompanyName(
                name = "UNIVERSIDAD POPULAR AUTONOMA DEL ESTADO DE PUEBLA",
                taxRegime = "[Régimen fiscal]",
                rfc = "UPA761015KQ0",
                mail = "correo@upaep.mx",
                address = "21 Sur No. 1103, Colonia Santiago",
                zipCode = "C.P. 72410",
                state = "Puebla,Puebla, México"
            ),
            CompanyName(
                name = "UNIDADES BASICAS",
                taxRegime = "[Régimen fiscal]",
                rfc = "UPAEPUPA761015KQ0",
                mail = "correo@unidadesbasicasupaep.edu.mx",
                address = "21 Sur No. 1103, Colonia Santiago",
                zipCode = "C.P. 72410",
                state = "Puebla,Puebla, México"
            ),
        )
    }
}