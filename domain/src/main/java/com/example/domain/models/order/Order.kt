package com.example.domain.models.order

data class Order(
    val id: String?,
    val data: OrderData,
    val items: List<OrderItem>
)