package com.example.data.repository

import com.example.data.storage.ShopStorage
import com.example.domain.models.shop.ShopPoint
import com.example.domain.repository.shop.ShopRepository

class ShopRepositoryImpl(private val storage: ShopStorage) : ShopRepository {
    override fun getPoints(): List<ShopPoint> {
        return storage.getPoints()
    }
}