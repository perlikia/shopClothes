package com.example.shopclothes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.category.Category
import com.example.domain.models.news.News
import com.example.domain.models.slide.Slide
import com.example.domain.usecase.category.GetSlidesUseCase
import com.example.domain.usecase.category.GetCategoryUseCase
import com.example.domain.usecase.category.GetNewsUseCase
import kotlinx.coroutines.launch

class CatalogRootViewModel(
    private val categoryUseCase: GetCategoryUseCase,
    private val slidesUseCase: GetSlidesUseCase,
    private val newsUseCase: GetNewsUseCase
) : ViewModel() {

    private val _slidesData : MutableLiveData<List<Slide>> = MutableLiveData()
    val slideData : LiveData<List<Slide>> = _slidesData

    private val _categoryData : MutableLiveData<List<Category>> = MutableLiveData()
    val categoryData : LiveData<List<Category>> = _categoryData

    private val _newsData : MutableLiveData<List<News>> = MutableLiveData()
    val newsData : LiveData<List<Category>> = _categoryData

    fun updateSlideData(){
        viewModelScope.launch {
            _slidesData.value = slidesUseCase.getSlides()
        }
    }

    fun updateCategoryData(){
        viewModelScope.launch {
            _categoryData.value = categoryUseCase.getRootCategories()
        }
    }

    fun updateNewsData(){
        viewModelScope.launch {
            _newsData.value = newsUseCase.getNews()
        }
    }

}