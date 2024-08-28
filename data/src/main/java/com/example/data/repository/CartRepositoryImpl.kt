package com.example.data.repository

import android.util.Log
import com.example.data.storage.CartStorage
import com.example.domain.models.cart.Cart
import com.example.domain.models.cart.CartItem
import com.example.domain.models.user.UserToken
import com.example.domain.repository.cart.CartRepository

class CartRepositoryImpl(val storage: CartStorage) : CartRepository {
    override fun getCartByUser(userToken: UserToken): Cart? {
        return storage.getCartByUser(userToken)
    }

    override fun addItemInCart(userToken: UserToken, cartItem: CartItem) {
        storage.addItemInCart(userToken, cartItem)
    }
}