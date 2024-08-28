package com.example.shopclothes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.models.cart.CartItem
import com.example.domain.models.product.FullProduct
import com.example.domain.models.product.Product
import com.example.domain.models.product.Size
import com.example.domain.models.user.UserToken
import com.example.domain.usecase.cart.GetCartUseCase
import com.example.domain.usecase.product.GetProductUseCase

class ProductPageViewModel(private val productsUseCase: GetProductUseCase, private val cartUseCase: GetCartUseCase) : ViewModel() {
    private val _product : MutableLiveData<FullProduct> = MutableLiveData()
    val product : LiveData<FullProduct> = _product

    private val _currentSize : MutableLiveData<Size?> = MutableLiveData()
    val currentSize : LiveData<Size?> = _currentSize

    fun addProductInCart(userToken: UserToken, callback: ()->Unit){
        cartUseCase.addItemInCart(
            userToken,
            CartItem(
                null,
                product.value!!,
                currentSize.value,
                1
            )
        )
        callback()
    }

    fun updateCurrentSize(size: Size?){
        _currentSize.value = size
    }

    fun updateProductById(productID: String){
        _product.value = productsUseCase.getProductByID(FullProduct::class.java, productID)
    }
}