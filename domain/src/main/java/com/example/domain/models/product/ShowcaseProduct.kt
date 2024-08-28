package com.example.domain.models.product

import com.example.domain.models.category.Category
import java.net.URI

class ShowcaseProduct(
    id: String,
    val price: Int? = null,
    val images: List<URI>,
    val brandID: String? = null,
    val categoryID: String? = null,
    val attrs : List<Attribute>? = null
) : Product(id)