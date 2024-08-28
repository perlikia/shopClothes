package com.example.shopclothes.presentation.view.blocks

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.domain.models.product.Product
import com.example.domain.models.product.ShowcaseProduct
import com.example.shopclothes.databinding.CategoryProductCardLayoutBinding
import com.example.shopclothes.utils.handlers.ImageHandler
import org.koin.core.context.GlobalContext.get

class ProductCardView: FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    private var callback: (()->Unit)? = null
    val cardBinding: CategoryProductCardLayoutBinding
    var product: ShowcaseProduct? = null
        set(value){
            field = value
            updateView()
        }

    init{
        cardBinding = CategoryProductCardLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        val root = cardBinding.root
        root.setOnClickListener{
            callback?.invoke()
        }

        updateView()
    }

    fun updateView(){
        val handler: ImageHandler = get().get()
        product?.let {
            cardBinding.priceLabel.text = "${it.price.toString()}₽"
            val image = product?.images?.firstOrNull()
            if(image != null){
                handler.setupProductImage(cardBinding.image, image.toString())
            }
        }
    }

    fun setCallback(callback: ()->Unit){
        this.callback = callback
    }
}