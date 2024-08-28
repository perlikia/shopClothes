package com.example.domain.usecase.shop

import com.example.domain.models.shop.ShopPoint
import com.example.domain.repository.shop.ShopRepository

class GetShopUseCase(private val repository: ShopRepository) {
    fun getPoints() : List<ShopPoint>{
        return repository.getPoints()
    }
}