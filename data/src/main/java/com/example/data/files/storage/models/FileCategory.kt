package com.example.data.files.storage.models

import kotlinx.serialization.Serializable

@Serializable
data class FileCategory (
    val id : Int,
    val title : String,
    val parent : Int?
)