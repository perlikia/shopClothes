package com.example.data.storage

import com.example.domain.models.user.FullUser
import com.example.domain.models.user.User
import com.example.domain.models.user.UserPhoneNumber
import com.example.domain.models.user.UserPrivateData
import com.example.domain.models.user.UserSizeData
import com.example.domain.models.user.UserToken

interface UserStorage {
    fun createOrUpdateUser(phoneNumber: UserPhoneNumber): UserToken
    fun getUserByToken(userToken: UserToken): FullUser?
    fun updatePrivateData(userToken: UserToken, privateData: UserPrivateData)
    fun updateSizeData(userToken: UserToken, sizeData: UserSizeData)
}