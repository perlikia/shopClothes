package com.example.shopclothes.presentation.view.blocks

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.shopclothes.databinding.NewsCardBinding

class NewsCardView : FrameLayout{
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    val cardBinding: NewsCardBinding

    init{
        cardBinding = NewsCardBinding.inflate(LayoutInflater.from(context), this, true)
    }
}