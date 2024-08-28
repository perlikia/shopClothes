package com.example.domain.repository.user

import com.example.domain.models.user.FullUser
import com.example.domain.models.user.UserPhoneNumber
import com.example.domain.models.user.UserPrivateData
import com.example.domain.models.user.UserSizeData
import com.example.domain.models.user.UserToken

interface UserRepository {
    fun createOrUpdateUser(phoneNumber: UserPhoneNumber): UserToken
    fun getUserData(userToken: UserToken) : FullUser?
    fun updatePrivateData(userToken: UserToken, privateData: UserPrivateData)
    fun updateSizeData(userToken: UserToken, sizeData: UserSizeData)
}