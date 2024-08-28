package com.example.data.repository

import com.example.data.storage.BrandStorage
import com.example.domain.models.brand.Brand
import com.example.domain.models.product.Product
import com.example.domain.models.product.ShowcaseProduct
import com.example.domain.repository.product.BrandRepository

class BrandRepositoryImpl(private val brandStorage: BrandStorage) : BrandRepository {
    override fun getBrandsByProducts(products: List<ShowcaseProduct>): List<Brand> {
        val productsBrands = products.map { it.brandID }
        return brandStorage.getBrands { predicate ->
            productsBrands.contains(predicate.id)
        }
    }
}