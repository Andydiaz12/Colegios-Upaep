package com.upaep.colegios.model.api.home

import com.google.gson.Gson
import com.upaep.colegios.model.base.ColegiosInterface
import com.upaep.colegios.model.entities.AnswerBack
import com.upaep.colegios.model.entities.home.Feature
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class HomeService @Inject constructor(private val api: ColegiosInterface) {
    suspend fun getFeatures(): AnswerBack<List<Feature>> {
        var features: List<Feature> = emptyList()
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getFeatures()
                when (response.body()?.statusCode) {
                    200 -> {
                        val gson = Gson()
                        features =
                            gson.fromJson(response.body()?.CRYPTDATA, Array<Feature>::class.java)
                                .toList()
                        AnswerBack.Success(features)
                    }
                    else -> {
                        AnswerBack.Error("Error")
                    }
                }
            } catch (e: IOException) {
                AnswerBack.NetworkError("Error de red")
            }
        }
    }
}