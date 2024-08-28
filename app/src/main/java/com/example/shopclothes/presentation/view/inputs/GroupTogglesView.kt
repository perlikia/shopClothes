package com.example.shopclothes.presentation.view.inputs

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.domain.models.product.AttributeKey
import com.example.domain.models.product.AttributeValue
import com.example.shopclothes.databinding.GroupFilterLayoutBinding
import com.example.shopclothes.utils.fields.ToggleField

class GroupTogglesView: FrameLayout{
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    private var callbackChange: ((AttributeValue, Boolean) -> Unit)? = null
    private val attribute: Pair<AttributeKey, ArrayList<ToggleField<AttributeValue>>>? = null
    val binding: GroupFilterLayoutBinding

    init{
        binding = GroupFilterLayoutBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    fun setCallback(callback: (AttributeValue, Boolean)->Unit){
        callbackChange = callback
    }

    fun updateAttribute(){
        val keyValue = attribute?.first
        val attrValues = attribute?.second

        binding.filterLabel.text = keyValue?.placeholder
        attrValues?.forEach{ attrValue ->
            ToggleView(
                context
            ).apply {
                val data = attrValue.data

                value = data.value
                label = data.placeholder
                isChecked = attrValue.isOn()
                setCallback{ status ->
                    callbackChange?.invoke(data, status)
                }

            }.let {
                binding.filtersList.addView(it)
            }
        }
    }
}