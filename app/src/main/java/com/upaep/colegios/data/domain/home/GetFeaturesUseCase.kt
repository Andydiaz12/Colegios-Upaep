package com.upaep.colegios.data.domain.home

import com.upaep.colegios.data.entities.home.Feature
import com.upaep.colegios.data.repository.home.HomeRepository
import javax.inject.Inject

class GetFeaturesUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(): List<Feature> {
        return homeRepository.getFeatures()
    }
}