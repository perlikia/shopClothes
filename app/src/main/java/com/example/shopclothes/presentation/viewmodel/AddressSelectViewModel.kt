package com.example.shopclothes.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.models.shop.ShopPoint
import com.example.domain.usecase.shop.GetShopUseCase
import com.example.files.AssetsHandler
import com.yandex.mapkit.mapview.MapView

class AddressSelectViewModel(
    private val shopUseCase: GetShopUseCase
) : ViewModel() {

    private val _mapStyle = MutableLiveData<String>()
    val mapStyle : LiveData<String> = _mapStyle

    private val _points = MutableLiveData<List<ShopPoint>>()
    val points : LiveData<List<ShopPoint>> = _points

    private val _selectedPoint = MutableLiveData<ShopPoint>()
    val selectedPoint: LiveData<ShopPoint> = _selectedPoint

    fun updateSelected(point: ShopPoint){
        _selectedPoint.value = point
    }

    fun updateMapStyle(context: Context){
        _mapStyle.value = AssetsHandler.getFileContent(context.assets, "map_style.json", AssetsHandler.Companion.Type.DATA)
    }

    fun updatePoints(){
        _points.value = shopUseCase.getPoints()
    }
}