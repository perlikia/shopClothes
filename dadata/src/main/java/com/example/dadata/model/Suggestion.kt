package com.example.dadata.model

import kotlinx.serialization.Serializable

@Serializable
data class Suggestion(
    val value: String,
    val unrestricted_value: String,
    val data: Address
)