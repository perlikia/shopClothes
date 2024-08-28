package com.example.domain.usecase.product

import com.example.domain.models.brand.Brand
import com.example.domain.models.product.Product
import com.example.domain.models.product.ShowcaseProduct
import com.example.domain.repository.product.BrandRepository

class GetBrandUseCase(private val brandRepository: BrandRepository) {
    fun getBrandsByProducts(products: List<ShowcaseProduct>) : List<Brand>{
        return brandRepository.getBrandsByProducts(products)
    }
}