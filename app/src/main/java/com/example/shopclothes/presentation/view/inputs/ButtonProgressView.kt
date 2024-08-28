package com.example.shopclothes.presentation.view.inputs

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.shopclothes.R
import com.example.shopclothes.databinding.ButtonProgressViewBinding

class ButtonProgressView : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    private var progress: Boolean = false
    fun isProgress(): Boolean{
        return progress
    }

    var text: String? = null
        set(value){
            field = value
            binding.button.text = value
        }

    private val binding: ButtonProgressViewBinding
    init {
        binding = ButtonProgressViewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
        stopProgress()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        binding.button.setOnClickListener(l)
    }

    fun startProgress(){
        binding.button.text = null
        binding.progressBar.visibility = VISIBLE
        progress = true
    }

    fun stopProgress(){
        binding.button.text = text
        binding.progressBar.visibility = GONE
        progress = false
    }

}