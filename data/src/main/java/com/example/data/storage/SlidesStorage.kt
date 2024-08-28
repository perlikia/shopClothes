package com.example.data.storage

import com.example.domain.models.slide.Slide

interface SlidesStorage {

    fun getSlides(): List<Slide>

}