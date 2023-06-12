package com.upaep.colegios.model.domain.home

import com.upaep.colegios.model.entities.home.Feature
import com.upaep.colegios.model.repository.home.HomeRepository
import javax.inject.Inject

class GetFeaturesUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(): List<Feature> {
        return homeRepository.getFeatures()
    }
}