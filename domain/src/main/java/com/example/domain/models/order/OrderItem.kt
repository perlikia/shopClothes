package com.example.domain.models.order

import com.example.domain.models.product.FullProduct
import com.example.domain.models.product.Size

data class OrderItem (
    val id: String,
    val size: Size?,
    val product: FullProduct,
    val count: Int,
    val price: Double
)