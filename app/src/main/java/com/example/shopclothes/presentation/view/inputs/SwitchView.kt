package com.example.shopclothes.presentation.view.inputs

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.shopclothes.databinding.SelectSwitchViewBinding

class SwitchView: FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    private var block: SelectSwitchViewBinding

    private var currentStatus = false
    private var callback: ((Boolean)->Unit)? = null

    fun setCurrentStatus(status: Boolean){
        currentStatus = status
        updateStatus()
    }

    private fun updateStatus(){
        block.switchBtn.isChecked = currentStatus
    }

    init{
        block = SelectSwitchViewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

        block.switchBtn.setOnCheckedChangeListener{ _, status ->
            currentStatus = status
            callback?.invoke(currentStatus)
        }

        updateStatus()
    }
}