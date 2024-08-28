package com.example.data.files.storage.models

import kotlinx.serialization.Serializable

@Serializable
data class FileBrand(
    val id: Int,
    val name: String
)