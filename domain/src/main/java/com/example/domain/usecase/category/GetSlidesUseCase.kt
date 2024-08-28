package com.example.domain.usecase.category

import com.example.domain.models.slide.Slide
import com.example.domain.repository.catalog.SlidesRepository

class GetSlidesUseCase(private val repository: SlidesRepository) {
    fun getSlides() : List<Slide>{
        return repository.getSlides()
    }
}