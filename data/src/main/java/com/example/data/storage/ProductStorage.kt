package com.example.data.storage

import com.example.domain.models.product.Product

interface ProductStorage {
    fun <T : Product> getProducts(productClass: Class<T>, predicate: (T) -> Boolean) : List<T>
}