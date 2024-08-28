package com.example.data.files.storage.models

import kotlinx.serialization.Serializable

@Serializable
data class FilePrivateData(
    val sex: String,
    val email: String?
)