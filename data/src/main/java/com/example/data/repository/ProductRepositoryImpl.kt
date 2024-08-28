package com.example.data.repository

import com.example.data.storage.ProductStorage
import com.example.domain.models.product.FullProduct
import com.example.domain.models.product.Product
import com.example.domain.models.product.ShowcaseProduct
import com.example.domain.repository.product.ProductRepository

class ProductRepositoryImpl(private val productStorage: ProductStorage) : ProductRepository {

    override fun <T : Product> getProductByID(productClass: Class<T>, productID: String): T {
        return productStorage.getProducts(productClass) { product ->
            product.id == productID
        }.first()
    }

    override fun getProductsByCategory(categoryID: String): List<ShowcaseProduct> {
        return productStorage.getProducts(ShowcaseProduct::class.java) { predicate ->
            predicate.categoryID == categoryID
        }
    }

    override fun getProductsByFilterCategory(
        categoryID: String,
        predicate: (ShowcaseProduct) -> Boolean
    ): List<ShowcaseProduct> {
        return productStorage.getProducts(ShowcaseProduct::class.java) { item ->
            item.categoryID == categoryID && predicate.invoke(item)
        }
    }

}