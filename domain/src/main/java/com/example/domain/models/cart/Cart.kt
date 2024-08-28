package com.example.domain.models.cart

data class Cart(
    val userID: String,
    val id: String?,
    var items: List<CartItem>
)