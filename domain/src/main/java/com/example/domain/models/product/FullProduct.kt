package com.example.domain.models.product

import com.example.domain.models.brand.Brand
import com.example.domain.models.category.Category
import java.net.URI
import java.net.URL

class FullProduct(
    id: String,
    val description: String? = null,
    val sizes: List<Size>? = null,
    val brand: Brand? = null,
    val images: List<URI>,
    val sizeSystem: String? = null, // TODO: Сделать класс размеров
    val gender: String? = null, // TODO: Сделать класс пола
    val name: String? = null,
    val modelName: String? = null,
    val price: Int? = null,
    val category : Category? = null,
    val attrs : List<Attribute>? = null
) : Product(id)