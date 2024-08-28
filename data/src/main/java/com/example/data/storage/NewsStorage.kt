package com.example.data.storage

import com.example.domain.models.news.News

interface NewsStorage {
    fun getNews() : List<News>
}