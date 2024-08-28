package com.example.data.files.storage.models

import com.example.domain.models.user.UserToken
import kotlinx.serialization.Serializable

@Serializable
data class FileUser(
    val id: String,
    val phone: String,
    var userToken: String? = null,
    var privateData: FilePrivateData? = null,
    var sizeData: FileUserSizeData? = null
)