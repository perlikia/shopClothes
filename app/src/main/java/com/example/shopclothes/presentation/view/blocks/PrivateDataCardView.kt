package com.example.shopclothes.presentation.view.blocks

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.shopclothes.databinding.PrivateDataCardLayoutBinding
import com.example.shopclothes.databinding.TextviewBasicBinding
import com.example.shopclothes.utils.fields.StringPair

class PrivateDataCardView: FrameLayout {
    private var redButtonCallback: (()->Unit)? = null
    private var values : Set<StringPair>? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    private val block: PrivateDataCardLayoutBinding

    init {
        val layoutInflater = LayoutInflater.from(context)

        block = PrivateDataCardLayoutBinding.inflate(layoutInflater, this, true)
        block.redDataBtn.setOnClickListener{
            redButtonCallback?.invoke()
        }
    }

    private fun updateContent(){
        val layoutInflater = LayoutInflater.from(context)

        block.privateDataList.removeAllViews()
        values?.forEach{ entry ->
            TextviewBasicBinding.inflate(layoutInflater, block.privateDataList, true).root.apply {
                text = entry.second
            }
        }
    }

    fun setValues(values: Set<StringPair>){
        this.values = values
        updateContent()
    }

    fun setCallback(callback: ()->Unit){
        redButtonCallback = callback
    }
}