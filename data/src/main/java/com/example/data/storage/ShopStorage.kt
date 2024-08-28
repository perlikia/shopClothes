package com.example.data.storage

import com.example.domain.models.shop.ShopPoint

interface ShopStorage {
    fun getPoints(): List<ShopPoint>
}