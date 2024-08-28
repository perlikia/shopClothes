package com.example.domain.repository.product

import com.example.domain.models.brand.Brand
import com.example.domain.models.product.Product
import com.example.domain.models.product.ShowcaseProduct

interface BrandRepository {
    fun getBrandsByProducts(products: List<ShowcaseProduct>) : List<Brand>
}