package com.example.data.storage

import com.example.domain.models.category.Category

interface CategoryStorage {
    fun getCategories(predicate: (Category)->Boolean) : List<Category>
}