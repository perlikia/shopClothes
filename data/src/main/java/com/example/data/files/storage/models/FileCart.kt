package com.example.data.files.storage.models

import kotlinx.serialization.Serializable

@Serializable
data class FileCart(
    val userID: String,
    val id: String,
    val list: List<FileCartItem>
)