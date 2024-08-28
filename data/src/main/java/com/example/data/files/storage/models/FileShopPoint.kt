package com.example.data.files.storage.models

import kotlinx.serialization.Serializable

@Serializable
data class FileShopPoint(
    val id: String,
    val name: String,
    val lat: Double,
    val lng: Double,
    val city: String,
    val cityGuid: String,
    val address: String
)