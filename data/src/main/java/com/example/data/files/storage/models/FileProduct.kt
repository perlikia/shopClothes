package com.example.data.files.storage.models

import kotlinx.serialization.Serializable

@Serializable()
data class FileProduct (
    val id: String,
    val price: Double,
    val photos: List<FileProductPhoto>,
    val category: Int? = null,
    val sizes: List<FileSize>? = null,
    val brand: FileProductBrand? = null
)