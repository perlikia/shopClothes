package com.example.domain.usecase.user

import com.example.domain.models.user.UserPhoneNumber
import com.example.domain.models.user.UserPrivateData
import com.example.domain.models.user.UserSizeData
import com.example.domain.models.user.UserToken
import com.example.domain.repository.user.UserRepository

class UpdateUserUseCase(private val userRepository: UserRepository) {
    fun createOrUpdateUser(phoneNumber: UserPhoneNumber): UserToken{
        return userRepository.createOrUpdateUser(phoneNumber)
    }

    fun updatePrivateData(userToken: UserToken, privateData: UserPrivateData){
        userRepository.updatePrivateData(userToken, privateData)
    }

    fun updateSizeData(userToken: UserToken, sizeData: UserSizeData){
        userRepository.updateSizeData(userToken, sizeData)
    }
}