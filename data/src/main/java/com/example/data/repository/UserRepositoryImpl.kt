package com.example.data.repository

import com.example.data.storage.UserStorage
import com.example.domain.models.user.FullUser
import com.example.domain.models.user.UserPhoneNumber
import com.example.domain.models.user.UserPrivateData
import com.example.domain.models.user.UserSizeData
import com.example.domain.models.user.UserToken
import com.example.domain.repository.user.UserRepository

class UserRepositoryImpl(private val userStorage: UserStorage) : UserRepository {
    override fun createOrUpdateUser(phoneNumber: UserPhoneNumber): UserToken {
        return userStorage.createOrUpdateUser(phoneNumber)
    }

    override fun getUserData(userToken: UserToken): FullUser? {
        return userStorage.getUserByToken(userToken)
    }

    override fun updatePrivateData(userToken: UserToken, privateData: UserPrivateData) {
        userStorage.updatePrivateData(userToken, privateData)
    }

    override fun updateSizeData(userToken: UserToken, sizeData: UserSizeData) {
        userStorage.updateSizeData(userToken, sizeData)
    }
}