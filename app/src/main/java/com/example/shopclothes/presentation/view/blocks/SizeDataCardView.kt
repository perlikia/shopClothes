package com.example.shopclothes.presentation.view.blocks

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.shopclothes.databinding.SizeDataCardLayoutBinding

class SizeDataCardView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    private var redButtonCallback: (()->Unit)? = null

    var footSize: Int? = null
        set(value){
            field = value
            updateValues()
        }
    var headSize: Int? = null
        set(value){
            field = value
            updateValues()
        }
    var waistSize: Int? = null
        set(value){
            field = value
            updateValues()
        }
    var heightSize: Int? = null
        set(value){
            field = value
            updateValues()
        }

    private val block: SizeDataCardLayoutBinding

    init{
        val layoutInflater = LayoutInflater.from(context)
        block = SizeDataCardLayoutBinding.inflate(layoutInflater, this, true)

        block.redSizeBtn.setOnClickListener{
            redButtonCallback?.invoke()
        }
    }

    fun setCallback(callback: ()->Unit){
        redButtonCallback = callback
    }

    fun updateValues(){
        block.footValue.text = (footSize ?: "-").toString()
        block.headValue.text = (headSize ?: "-").toString()
        block.waistValue.text = (waistSize ?: "-").toString()
        block.heightValue.text = (heightSize ?: "-").toString()
    }
}