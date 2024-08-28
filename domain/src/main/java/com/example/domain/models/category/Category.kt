package com.example.domain.models.category

data class Category(
    val id : String,
    val name : String? = null,
    val parent : String? = null,
    val imagePath : String? = null,
    val hasProducts : Boolean? = null
)