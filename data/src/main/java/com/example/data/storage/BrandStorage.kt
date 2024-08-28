package com.example.data.storage

import com.example.domain.models.brand.Brand

interface BrandStorage {
    fun getBrands(predicate: (Brand) -> Boolean) : List<Brand>
}