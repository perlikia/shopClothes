package com.example.shopclothes.presentation.viewmodel

import android.content.res.AssetManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dadata.DadataHandler
import com.example.dadata.model.Address
import com.example.files.AssetsHandler
import com.example.files.FileHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddressCourierViewModel(private val assetManager: AssetManager) : ViewModel() {

    private val _mapStyle: MutableLiveData<String> = MutableLiveData()
    val mapStyle: LiveData<String> = _mapStyle

    private val _address: MutableLiveData<Address> = MutableLiveData()
    val address: LiveData<Address> = _address

    fun updateMapStyle(){
        _mapStyle.value = AssetsHandler.getFileContent(assetManager, "map_style.json", AssetsHandler.Companion.Type.DATA)
    }

    fun updateAddressCourierPoint(lat: Double, lng: Double){
        viewModelScope.launch(Dispatchers.IO) {
            val address = DadataHandler.geolocateAddress(lat, lng)
            _address.postValue(address ?: null)
        }
    }
}