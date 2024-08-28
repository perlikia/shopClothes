package com.example.data.files.storage

import android.content.Context
import android.util.Log
import com.example.data.files.storage.models.FilePrivateData
import com.example.data.files.storage.models.FileUser
import com.example.data.files.storage.models.FileUserSizeData
import com.example.data.storage.UserStorage
import com.example.domain.dictionary.Sex
import com.example.domain.models.user.FullUser
import com.example.domain.models.user.UserPhoneNumber
import com.example.domain.models.user.UserPrivateData
import com.example.domain.models.user.UserSizeData
import com.example.domain.models.user.UserToken
import com.example.files.FileHandler
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID

class FilesUserStorageImpl(private val context: Context) : UserStorage {
    val userFilename = "user.json"

    fun checkFileExists(){
        val exists = FileHandler.fileExists(context, userFilename, FileHandler.Type.USER_DATA)
        val result = Json {
            ignoreUnknownKeys = true
        }.encodeToString(
            listOf(FileUser("-1", "BasicUser"))
        )
        if(exists.not()){
            writeDataFile(result)
        }
    }

    fun updateList(users: List<FileUser>){
        val content = Json{
            ignoreUnknownKeys = true
        }.encodeToString(users)
        writeDataFile(content)
    }

    fun writeDataFile(result: String){
        FileHandler.writeContentInFile(
            context,
            userFilename,
            FileHandler.Type.USER_DATA,
            result
        )
    }

    fun getFileUsers() : List<FileUser>{
        checkFileExists()
        val content = FileHandler.getContentFromFile(context, userFilename, FileHandler.Type.USER_DATA)
        val users = Json{
           ignoreUnknownKeys = true
        }.decodeFromString<List<FileUser>>(content)
        return users
    }

    override fun createOrUpdateUser(phoneNumber: UserPhoneNumber): UserToken {
        val list = getFileUsers()
        val token = generateUserToken()

        val user = list.firstOrNull {
            it.phone == phoneNumber.phone
        }

        if(user == null){
            updateList(
                ArrayList(list).apply {
                    add(
                        FileUser(
                            generateUserToken(),
                            phoneNumber.phone,
                            token
                        )
                    )
                }
            )
        }
        else{
            user.userToken = token
        }

        return UserToken(token)
    }

    private fun generateUserToken(): String{
        return UUID.randomUUID().toString()
    }

    override fun getUserByToken(userToken: UserToken) : FullUser? {
        val users = getFileUsers()
        val result = users.filter{ item ->
            item.userToken == userToken.token
        }.map(this::normalizeUser).firstOrNull()
        return result
    }

    override fun updatePrivateData(userToken: UserToken, privateData: UserPrivateData) {
        val filePrivateData = decompilePrivateData(privateData)
        val user = getUserByToken(userToken)

        updateList(getFileUsers().apply {
            filter { item -> item.phone == user!!.phone.phone }.first().privateData = filePrivateData
        })
    }

    override fun updateSizeData(userToken: UserToken, sizeData: UserSizeData) {
        val fileSizeData = decompileSizeData(sizeData)
        val user = getUserByToken(userToken)
        updateList(getFileUsers().apply {
            filter { item -> item.phone == user!!.phone.phone }.first().sizeData = fileSizeData
        })
    }

    fun decompileSizeData(sizeData: UserSizeData) : FileUserSizeData{
        return FileUserSizeData(
            sizeData.sizeHead,
            sizeData.sizeFoot,
            sizeData.sizeWaist,
            sizeData.bodyHeight
        )
    }

    fun decompilePrivateData(privateData: UserPrivateData) : FilePrivateData{
        return FilePrivateData(
            privateData.sex.key,
            privateData.email
        )
    }

    fun normalizeUser(fileUser: FileUser) : FullUser{
        return FullUser(
            fileUser.id,
            phone = UserPhoneNumber(fileUser.phone),
            privateData = fileUser.privateData?.let { normalizePrivateData(it) },
            sizeData = fileUser.sizeData?.let { normalizeSizeData(it) }
        )
    }

    fun normalizeSizeData(sizeData: FileUserSizeData) : UserSizeData{
        return UserSizeData(
            sizeData.sizeHead,
            sizeData.sizeFoot,
            sizeData.sizeWaist,
            sizeData.bodyHeight
        )
    }

    fun normalizePrivateData(privateData: FilePrivateData) : UserPrivateData{
        return UserPrivateData(
            email = privateData.email,
            sex = Sex.convertToSex(privateData.sex)
        )
    }
}