package com.example.domain.repository.shop

import com.example.domain.models.shop.ShopPoint

interface ShopRepository {
    fun getPoints() : List<ShopPoint>
}