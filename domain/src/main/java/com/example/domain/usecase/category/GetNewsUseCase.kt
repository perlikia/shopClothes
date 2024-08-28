package com.example.domain.usecase.category

import com.example.domain.models.news.News
import com.example.domain.repository.catalog.NewsRepository

class GetNewsUseCase(private val newsRepository: NewsRepository) {
    fun getNews() : List<News>{
        return newsRepository.getNews()
    }
}