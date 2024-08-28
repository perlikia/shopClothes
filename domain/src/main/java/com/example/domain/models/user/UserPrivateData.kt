package com.example.domain.models.user

import com.example.domain.dictionary.Sex

class UserPrivateData(
    val first_name: String? = null,
    val second_name: String? = null,
    val last_name: String? = null,
    val email : String? = null,
    val sex: Sex
)