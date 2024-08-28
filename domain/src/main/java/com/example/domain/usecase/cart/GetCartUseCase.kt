package com.example.domain.usecase.cart

import com.example.domain.models.cart.Cart
import com.example.domain.models.cart.CartItem
import com.example.domain.models.user.UserToken
import com.example.domain.repository.cart.CartRepository

class GetCartUseCase(private val cartRepository: CartRepository) {
    fun getCartByUser(userToken: UserToken) : Cart? {
        return cartRepository.getCartByUser(userToken)
    }

    fun addItemInCart(userToken: UserToken, cartItem: CartItem){
        cartRepository.addItemInCart(userToken, cartItem)
    }
}