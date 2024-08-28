package com.example.domain.models.cart

import com.example.domain.models.product.FullProduct
import com.example.domain.models.product.Size

data class CartItem(
    val id: String?,
    val product: FullProduct,
    val size: Size?,
    val count: Int
)