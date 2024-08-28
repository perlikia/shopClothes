package com.example.shopclothes.presentation.view.inputs

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.domain.repository.cart.CartRepository
import com.example.shopclothes.databinding.ToggleButtonBinding

class ToggleView: FrameLayout{

    var value: String? = null
    var label: String? = null
        set(value){
            field = value
            updateLabel()
        }
    var isChecked: Boolean = false
        set(value){
            field = value
            updateStatus()
        }

    private var changeCallback: ((Boolean)->Unit)? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    val cardBinding: ToggleButtonBinding

    fun updateLabel(){
        cardBinding.root.textOn = label
        cardBinding.root.textOff = label
    }

    fun updateStatus(){
        cardBinding.root.isChecked = isChecked
    }

    fun setCallback(callback: (Boolean)->Unit){
        changeCallback = callback
    }

    init{
        cardBinding = ToggleButtonBinding.inflate(LayoutInflater.from(context), this, true)
        cardBinding.root.setOnCheckedChangeListener { _, status ->
            isChecked = status
            changeCallback?.invoke(isChecked)
        }
        updateLabel()
        updateStatus()
    }
}