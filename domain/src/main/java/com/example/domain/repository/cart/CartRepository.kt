package com.example.domain.repository.cart

import com.example.domain.models.cart.Cart
import com.example.domain.models.cart.CartItem
import com.example.domain.models.user.UserToken

interface CartRepository {
    fun getCartByUser(userToken: UserToken) : Cart?
    fun addItemInCart(userToken: UserToken, cartItem: CartItem)
}