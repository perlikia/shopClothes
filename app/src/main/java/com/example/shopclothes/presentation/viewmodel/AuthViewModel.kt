package com.example.shopclothes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.user.UserPhoneNumber
import com.example.domain.models.user.UserToken
import com.example.domain.usecase.user.UpdateUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(private val userUseCase: UpdateUserUseCase) : ViewModel() {
    enum class Type{
        None,
        Registration,
        Authorisation
    }

    private val _type : MutableLiveData<Type> = MutableLiveData(Type.None)
    val type : LiveData<Type> = _type

    private val _phone : MutableLiveData<UserPhoneNumber> = MutableLiveData()
    val phone : LiveData<UserPhoneNumber> = _phone

    fun changeType(authType: Type){
        _type.value = authType
    }

    fun insertPhone(number: String){
        _phone.value = UserPhoneNumber(number)
    }

    fun isValidPhone() : Boolean{
        return if(phone.value != null) phone.value!!.isValidPhone() else false
    }

    fun updateUser(callback: (UserToken)->Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val result = userUseCase.createOrUpdateUser(phone.value!!)
            launch(Dispatchers.Main) {
                callback(result)
            }
        }
    }
}