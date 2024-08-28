package com.example.domain.usecase.product

import com.example.domain.models.product.Product
import com.example.domain.models.product.ShowcaseProduct
import com.example.domain.repository.product.ProductRepository

class GetProductUseCase(private val productRepository: ProductRepository) {

    fun <T : Product> getProductByID(productClass: Class<T>, productID: String) : T{
        return productRepository.getProductByID(productClass, productID)
    }

    fun getProductsByCategory(categoryID: String) : List<ShowcaseProduct>{
        return productRepository.getProductsByCategory(categoryID)
    }

    fun getProductsByCategoryAndFilters(categoryID: String, filters: (ShowcaseProduct)->Boolean) : List<ShowcaseProduct>{
        return productRepository.getProductsByFilterCategory(categoryID, filters)
    }
}