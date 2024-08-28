package com.example.data.files.storage.models

import com.example.domain.models.product.CartProduct
import com.example.domain.models.product.Size
import kotlinx.serialization.Serializable

@Serializable
data class FileCartItem(
    val id: String,
    val productID: String,
    val size: FileSize?,
    val count: Int
)