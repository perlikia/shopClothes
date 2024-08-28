package com.example.shopclothes.presentation.view.blocks

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.shopclothes.databinding.CardBlockViewBinding

class CardBlockView : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    var header: String? = null
        set(value){
            field = value
            updateLabels()
        }
    var text: String? = null
        set(value){
            field = value
            updateLabels()
        }

    private var callback: (()->Unit)? = null
    private var binding: CardBlockViewBinding

    init {
        binding = CardBlockViewBinding.inflate(LayoutInflater.from(context), this, true)
        binding.root.setOnClickListener{
            callback?.invoke()
        }
        updateLabels()
    }

    fun setCallback(callback: ()->Unit){
        this.callback = callback
    }

    private fun updateLabels(){
        binding.header.text = header
        binding.text.text = text
    }
}