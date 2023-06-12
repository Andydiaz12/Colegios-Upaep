package com.upaep.colegios.model.repository.home

import com.upaep.colegios.model.api.home.HomeService
import com.upaep.colegios.model.database.homedao.HomeDao
import com.upaep.colegios.model.entities.AnswerBack
import com.upaep.colegios.model.entities.home.Feature
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val homeService: HomeService,
    private val homeDao: HomeDao
) {
    suspend fun getFeatures(): List<Feature> {
        val features = homeService.getFeatures()
        if (features is AnswerBack.Success) {
            deleteFeatures()
            addFeatures(features = features.data)
            return getFeaturesFromDB()
        }
//        when(features) {
//            is AnswerBack.Success -> {
//                // BORRAR DATOS DE BD DE FEATURES Y LLENAR NUEVAMENTE
//            }
//            is AnswerBack.Error -> {
//                // MOSTRAR MENSAJE A USUARIO
//            }
//            is AnswerBack.NetworkError -> {
//                // MOSTRAR MENSAJE DE ERROR DE INTERNET
//            }
//            is AnswerBack.AccessDenied -> {}
//        }
        return emptyList()
    }

    suspend fun deleteFeatures() {
        homeDao.deleteFeatures()
    }

    suspend fun addFeatures(features: List<Feature>) {
        homeDao.addFeatures(features)
    }

    suspend fun getFeaturesFromDB() : List<Feature> {
        return homeDao.getFeatures()
    }
}