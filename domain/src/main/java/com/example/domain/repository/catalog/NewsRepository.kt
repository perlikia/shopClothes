package com.example.domain.repository.catalog

import com.example.domain.models.news.News

interface NewsRepository {
    fun getNews() : List<News>
}