package com.example.domain.models.order

import com.example.domain.dictionary.OrderType
import com.example.domain.models.shop.ShopPoint
import java.time.LocalDateTime

data class OrderData(
    val type: OrderType,

    val receiver: OrderUserData,

    val courierAddress: OrderCourierAddress?,
    val courierTime: LocalDateTime?,
    val courierLeaveDoor: Boolean?,
    val courierNotCall: Boolean?,
    val courierCost: Int?,

    val selectedShop: ShopPoint?,

    val allSum: Int
)