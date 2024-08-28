package com.example.domain.models.user

data class UserAddress(
    val geo_lat: String?,
    val geo_lon: String?,

    val street: String,
    val house: String,
    val apartment: String,
    val entrance: String?,
    val intercom: String?
)