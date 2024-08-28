package com.example.dadata.model

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val geo_lat: String,
    val geo_lon: String,

    val street: String?,
    val house: String?,
    val entrance: String?
)