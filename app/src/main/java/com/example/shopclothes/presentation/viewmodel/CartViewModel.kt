package com.example.shopclothes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.cart.Cart
import com.example.domain.models.cart.CartItem
import com.example.domain.models.user.User
import com.example.domain.models.user.UserToken
import com.example.domain.usecase.cart.GetCartUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(private val cartUseCase: GetCartUseCase): ViewModel() {
    private val _cart = MutableLiveData<Cart>()
    val cart: LiveData<Cart> = _cart

    fun updateValueItem(userToken: UserToken, item: CartItem, value: Int){
        viewModelScope.launch {
            cartUseCase.addItemInCart(
                userToken,
                CartItem(
                    item.id,
                    item.product,
                    item.size,
                    value
                )
            )
            updateCartByUser(userToken)
        }
    }

    fun updateCartByUser(userToken: UserToken){
        viewModelScope.launch(Dispatchers.IO) {
            _cart.postValue(
                cartUseCase.getCartByUser(userToken)
            )
        }
    }
}