package com.example.shopclothes.presentation.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.user.FullUser
import com.example.domain.models.user.UserPrivateData
import com.example.domain.models.user.UserSizeData
import com.example.domain.models.user.UserToken
import com.example.domain.usecase.user.GetUserUseCase
import com.example.domain.usecase.user.UpdateUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainContextViewModel(
    private val sharedPreferences: SharedPreferences,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {
    private val _user : MutableLiveData<FullUser?> = MutableLiveData()
    val user : LiveData<FullUser?> = _user

    val observer = { user: FullUser? ->

    }

    override fun onCleared() {
        super.onCleared()
        user.removeObserver(observer)
    }

    init {
        user.observeForever(observer)
    }

    fun saveToken(token: String){
        sharedPreferences.edit().apply{
            putString("currentToken", token)
            commit()
        }
    }

    fun getToken(): UserToken?{
        val token = sharedPreferences.getString("currentToken", null)
        return token?.let { UserToken(it) }
    }

    fun checkAndUpdateUser(callbackProcess: ()->Unit){
        val token = getToken()
        if(token == null) {
            _user.value = null
            callbackProcess.invoke()
        }
        else{
            updateUser(callbackProcess)
        }
    }

    private fun updateUser(callbackProcess: ()->Unit){
        viewModelScope.launch(Dispatchers.IO){
            val user = getUserUseCase.getUserData(getToken()!!)
            _user.postValue(user)
            viewModelScope.launch(Dispatchers.Main) {
                callbackProcess.invoke()
            }
        }
    }

    fun updateUserPrivateData(privateData: UserPrivateData){
        viewModelScope.launch(Dispatchers.IO){
            updateUserUseCase.updatePrivateData(getToken()!!, privateData)
            updateUser(){}
        }
    }

    fun updateUserSizeData(sizeData: UserSizeData){
        viewModelScope.launch(Dispatchers.IO){
            updateUserUseCase.updateSizeData(getToken()!!, sizeData)
            updateUser(){}
        }
    }
}