package com.example.dadata.model

import kotlinx.serialization.Serializable

@Serializable
data class Point(
    val lat: Double,
    val lon: Double
)