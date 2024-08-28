package com.example.domain.repository.product

import com.example.domain.models.category.Category
import com.example.domain.models.product.FullProduct
import com.example.domain.models.product.Product
import com.example.domain.models.product.ShowcaseProduct

interface ProductRepository {
    fun <T : Product> getProductByID(productClass: Class<T>, productID: String) : T
    fun getProductsByCategory(categoryID: String): List<ShowcaseProduct>
    fun getProductsByFilterCategory(
        categoryID: String,
        predicate: (ShowcaseProduct) -> Boolean
    ): List<ShowcaseProduct>
}