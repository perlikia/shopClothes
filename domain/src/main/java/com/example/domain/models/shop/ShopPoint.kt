package com.example.domain.models.shop

data class ShopPoint(
    val id: String,
    val name: String,
    val lat: Double,
    val lng: Double,
    val city: String,
    val cityGuid: String,
    val address: String
)