package com.example.domain.models.user

data class UserPhoneNumber(val phone: String) {

    fun isValidPhone() : Boolean{
        return phone.length == 11
    }

}