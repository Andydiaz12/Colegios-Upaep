package com.upaep.colegios.data.entities.home

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Feature(
    @PrimaryKey
    val featureId: Int,
    val featureName: String,
    val featureIcon: String
)
