package com.example.shopclothes.presentation.view.blocks

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.domain.models.category.Category
import com.example.shopclothes.databinding.CategoryCardLayoutBinding
import com.example.shopclothes.utils.handlers.ImageHandler
import org.koin.core.context.GlobalContext.get

class CategoryCardView: FrameLayout{
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    private var callback: (()->Unit)? = null

    private val cardBinding: CategoryCardLayoutBinding
    var category: Category? = null
        set(value){
            field = value
            updateView()
        }

    fun setCallback(callback: (()->Unit)){
        this.callback = callback
    }

    fun updateView(){
        cardBinding.categoryName.text = category?.name
        category?.imagePath?.let {
            val imageHandler: ImageHandler = get().get()
            imageHandler.setupCategoryImage(cardBinding.imageView, it)
        }
    }

    init{
        cardBinding = CategoryCardLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        val root = cardBinding.root
        root.setOnClickListener{
            callback?.invoke()
        }

        updateView()
    }
}