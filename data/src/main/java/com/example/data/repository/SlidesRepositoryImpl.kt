package com.example.data.repository

import com.example.data.storage.SlidesStorage
import com.example.domain.models.slide.Slide
import com.example.domain.repository.catalog.SlidesRepository

class SlidesRepositoryImpl(private val slidesStorage: SlidesStorage) : SlidesRepository {

    override fun getSlides(): List<Slide> {
        return slidesStorage.getSlides()
    }

}