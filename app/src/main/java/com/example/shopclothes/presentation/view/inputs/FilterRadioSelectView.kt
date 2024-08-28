package com.example.shopclothes.presentation.view.inputs

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import com.example.shopclothes.databinding.RadioBlockLayoutBinding
import com.example.shopclothes.presentation.sheet.MapSelectBottomSheet
import com.example.shopclothes.utils.fields.StringPair

class FilterRadioSelectView: FrameLayout{
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    private var callback: ((String?)->Unit)? = null
    var label: String? = null
        set(value){
            field = value
            updateLabels()
        }
    private var values: Set<StringPair>? = null
    private var selectedValue: String? = null

    var fragmentManager: FragmentManager? = null
    val binding: RadioBlockLayoutBinding
    init{
        binding = RadioBlockLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        binding.root.setOnClickListener{
            if(values != null && fragmentManager != null){
                MapSelectBottomSheet(
                    selectedValue,
                    label,
                    values!!
                ){ newValue ->
                    selectedValue = newValue
                    updateLabels()
                    callback?.invoke(selectedValue)
                }.show(fragmentManager!!, null)
            }
        }
        updateLabels()
    }

    fun setCallback(callback: (String?)->Unit){
        this.callback = callback
    }

    fun setValues(selectedValue: String?, values: Set<StringPair>){
        this.selectedValue = selectedValue
        this.values = values
        updateLabels()
    }

    fun updateLabels(){
        binding.labelText.text = label
        binding.labelValue.text = values?.first { predicate -> predicate.first == selectedValue }?.second
    }
}