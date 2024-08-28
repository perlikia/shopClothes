package com.example.domain.usecase.user

import com.example.domain.models.user.FullUser
import com.example.domain.models.user.UserToken
import com.example.domain.repository.user.UserRepository

class GetUserUseCase(private val userRepository: UserRepository) {
    fun getUserData(userToken: UserToken) : FullUser?{
        return userRepository.getUserData(userToken)
    }
}