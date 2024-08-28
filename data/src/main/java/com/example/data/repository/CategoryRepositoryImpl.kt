package com.example.data.repository

import com.example.data.storage.CategoryStorage
import com.example.domain.models.category.Category
import com.example.domain.repository.catalog.CategoryRepository

class CategoryRepositoryImpl(private val categoryStorage: CategoryStorage) : CategoryRepository {
    override fun getAllCategories(): List<Category> {
        return categoryStorage.getCategories{ predicate ->
            true
        }
    }

    override fun getRootCategories(): List<Category> {
        return categoryStorage.getCategories{ predicate ->
            predicate.parent == null
        }
    }

    override fun getSubcategories(categoryID: String): List<Category> {
        return categoryStorage.getCategories { predicate ->
            predicate.parent == categoryID
        }
    }

    override fun getCategoryByID(categoryID: String): Category {
        return categoryStorage.getCategories { predicate ->
            predicate.id == categoryID
        }.first()
    }
}