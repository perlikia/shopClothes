package com.example.data.files.storage

import com.example.data.storage.NewsStorage
import com.example.domain.models.news.News

class FilesNewsStorageImpl : NewsStorage {
    override fun getNews(): List<News> {
        return listOf(
            News("1"),
            News("1"),
            News("1"),
            News("1")
        )
    }
}