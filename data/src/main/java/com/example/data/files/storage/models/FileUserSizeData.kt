package com.example.data.files.storage.models

import kotlinx.serialization.Serializable

@Serializable
class FileUserSizeData(
    val sizeHead : Int? = null,
    val sizeFoot : Int? = null,
    val sizeWaist : Int? = null,
    val bodyHeight : Int? = null,
    val bodyWeight : Int? = null
)