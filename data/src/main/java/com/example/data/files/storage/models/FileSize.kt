package com.example.data.files.storage.models

import kotlinx.serialization.Serializable

@Serializable
data class FileSize(
    val size: String,
    val brand_size: String? = null,
    val brand_size_id: Int? = null
)