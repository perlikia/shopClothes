package com.example.shopclothes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.models.shop.ShopPoint
import com.example.domain.models.user.UserAddress
import com.example.domain.models.user.UserPrivateData

class OrderViewModel : ViewModel() {
    enum class Type(val placeholder: String){
        Pickup("Самовывоз"),
        Courier("Доставка курьером")
    }

    private val _recipient = MutableLiveData<UserPrivateData>()
    val recipient: LiveData<UserPrivateData> = _recipient
    fun setRecipient(data: UserPrivateData){
        _recipient.value = data
    }


    private val _userSelectAddress = MutableLiveData<UserAddress>()
    val userSelectAddress: LiveData<UserAddress> = _userSelectAddress
    fun setUserAddress(address: UserAddress){
        _userSelectAddress.value = address
    }

    private val _shopPointAddress = MutableLiveData<ShopPoint>()
    val shopPointAddress: LiveData<ShopPoint> = _shopPointAddress
    fun setShopPoint(shopPoint: ShopPoint){
        _shopPointAddress.value = shopPoint
    }

    private val _tabType = MutableLiveData(Type.Pickup)
    val tabType : LiveData<Type> = _tabType
    fun setTabType(type: Type){
        _tabType.value = type
    }
}