package com.example.domain.usecase.category

import com.example.domain.models.category.Category
import com.example.domain.repository.catalog.CategoryRepository

class GetCategoryUseCase(private val categoryRepository: CategoryRepository) {

    fun getCategoryByID(categoryID: String) : Category{
        return categoryRepository.getCategoryByID(categoryID)
    }

    fun getSubcategories(categoryID: String): List<Category>{
        return categoryRepository.getSubcategories(categoryID)
    }

    fun getCategories() : List<Category>{
        return categoryRepository.getAllCategories()
    }

    fun getRootCategories() : List<Category>{
        return categoryRepository.getRootCategories()
    }
}