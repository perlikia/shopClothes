package com.example.domain.repository.catalog

import com.example.domain.models.slide.Slide

interface SlidesRepository {
    fun getSlides() : List<Slide>
}