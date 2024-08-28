package com.example.data.files.storage

import com.example.data.storage.SlidesStorage
import com.example.domain.models.slide.Slide

class FilesSlidesStorageImpl : SlidesStorage {
    override fun getSlides(): List<Slide> {
        return listOf(Slide(""), Slide(""), Slide(""))
    }
}