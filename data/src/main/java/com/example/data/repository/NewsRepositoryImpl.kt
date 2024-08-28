package com.example.data.repository

import com.example.data.storage.NewsStorage
import com.example.domain.models.news.News
import com.example.domain.repository.catalog.NewsRepository

class NewsRepositoryImpl(private val newsStorage: NewsStorage) : NewsRepository {

    override fun getNews(): List<News> {
        return newsStorage.getNews()
    }

}