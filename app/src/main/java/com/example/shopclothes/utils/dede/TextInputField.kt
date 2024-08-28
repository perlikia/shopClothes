package com.example.shopclothes.utils.dede

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.shopclothes.databinding.TextInputViewBinding
import com.google.android.material.textfield.TextInputLayout

open class TextInputField(key: String) : InputField(key) {

    open fun callbackAfterCreateView(binding: TextInputViewBinding){

    }

    override fun createView(
        layoutInflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ) : TextInputLayout {
        val binding = TextInputViewBinding.inflate(layoutInflater, parent, attachToParent)
        callbackAfterCreateView(binding)
        return binding.root
    }
}