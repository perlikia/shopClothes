package com.example.domain.models.user

sealed class User(
    open val id: String?,
    val phone: UserPhoneNumber
)