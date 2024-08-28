package com.example.shopclothes.utils.dede

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.shopclothes.presentation.sheet.MapSelectBottomSheet
import com.example.shopclothes.utils.fields.StringPair

class RadioInputField(
    key: String,
    private val placeholder: String,
    private val defaultValue: String,
    private val values: Pair<String, Set<StringPair>>,
    private val fragmentManager: FragmentManager
) : InputField(key) {

    /*
    fun updateLabels(binding: RadioFilterBlockLayoutBinding, value: String){
        binding.labelText.text = placeholder
        binding.labelValue.text = values.second.first { predicate -> predicate.first == value }.second
    }

    override fun createView(
        layoutInflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ): View {
        var currentValue = defaultValue
        val binding = RadioFilterBlockLayoutBinding.inflate(
            layoutInflater,
            parent,
            attachToParent
        ).apply {
            updateLabels(this, currentValue)
            root.setOnClickListener{
                MapSelectBottomSheet(
                    currentValue,
                    values
                ){ newValue ->
                    currentValue = newValue
                    updateLabels(this, currentValue)
                }.show(fragmentManager, null)
            }
        }
        return binding.root
    }
*/
    override fun createView(
        layoutInflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ): View {
        TODO("Not yet implemented")
    }
}