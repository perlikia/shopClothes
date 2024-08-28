package com.example.domain.repository.catalog
import com.example.domain.models.category.Category

interface CategoryRepository{
    fun getAllCategories() : List<Category>
    fun getRootCategories() : List<Category>
    fun getSubcategories(categoryID: String): List<Category>
    fun getCategoryByID(categoryID: String) : Category
}