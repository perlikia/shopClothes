package com.example.shopclothes.presentation.view.blocks

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.domain.models.cart.Cart
import com.example.domain.models.cart.CartItem
import com.example.shopclothes.databinding.CartItemCardLayoutBinding

class CartItemCardView: FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    private var currentValueCallback: ((Int)->Unit)? = null

    private val block: CartItemCardLayoutBinding
    private var item: CartItem? = null

    fun getItemId(): String{
        return item!!.id!!
    }

    fun setCartItem(item: CartItem){
        this.item = item
        updateLabels()
    }

    private fun updateLabels(){
        val requireItem = item!!
        block.countLabel.text = requireItem.count.toString()

        val price = requireItem.product.price!!
        val count = requireItem.count
        block.sumLabel.text = "$${price}x${count}=${price * count}"
    }

    private fun changeValue(action: Int){
        currentValueCallback?.invoke(action + item!!.count)
    }

    fun setCallback(callback: (Int)->Unit){
        currentValueCallback = callback
    }

    init{
        block = CartItemCardLayoutBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

        block.minusBtn.setOnClickListener{
            changeValue(-1)
        }

        block.plusBtn.setOnClickListener{
            changeValue(1)
        }
    }
}