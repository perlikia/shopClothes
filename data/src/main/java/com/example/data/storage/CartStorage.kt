package com.example.data.storage

import com.example.domain.models.cart.Cart
import com.example.domain.models.cart.CartItem
import com.example.domain.models.user.UserToken

interface CartStorage {
    fun getCartByUser(userToken: UserToken) : Cart?
    fun addItemInCart(userToken: UserToken, cartItem: CartItem)
}