package com.example.shopclothes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.models.category.Category
import com.example.domain.usecase.category.GetCategoryUseCase

class CategoryViewModel(private val categoryUseCase: GetCategoryUseCase) : ViewModel() {

    private val _subcategoriesData : MutableLiveData<List<Category>> = MutableLiveData()
    val subcategoriesData : LiveData<List<Category>> = _subcategoriesData

    private val _initStatusData : MutableLiveData<Boolean> = MutableLiveData(false)
    val initStatusData : LiveData<Boolean> = _initStatusData

    private val _categoryData : MutableLiveData<Category> = MutableLiveData()
    val categoryData : LiveData<Category> = _categoryData

    private val _categoryIDData : MutableLiveData<String?> = MutableLiveData()
    val categoryIDData : LiveData<String?> = _categoryIDData

    fun setCategoryID(id: String?){
        _categoryIDData.value = id
    }

    fun init(){
        _initStatusData.value = true
    }

    fun updateCategories(){
        categoryIDData.value?.let {
            _categoryData.value = categoryUseCase.getCategoryByID(categoryIDData.value!!)
            _subcategoriesData.value = categoryUseCase.getSubcategories(categoryIDData.value!!)
        }
    }

}