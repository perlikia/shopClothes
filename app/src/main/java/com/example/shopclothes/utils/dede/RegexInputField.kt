package com.example.shopclothes.utils.dede

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.example.shopclothes.databinding.TextInputViewBinding
import com.google.android.material.textfield.TextInputLayout

abstract class RegexInputField(
    key: String,
    private val valueChangeCallback: (isValid: Boolean, value: String)->Unit
) : TextInputField(key) {
    abstract fun isValid(value: String) : Boolean

    private fun callbackChange(editText: EditText){
        val value = editText.text.toString()
        val valid = isValid(value)
        if(valid.not()){
            editText.error = "Неправильный формат ввода"
        }
        else{
            editText.error = null
        }
        valueChangeCallback(valid, value)
    }

    override fun callbackAfterCreateView(binding: TextInputViewBinding) {
        super.callbackAfterCreateView(binding)
        val editText = binding.editText

        editText.onFocusChangeListener = View.OnFocusChangeListener { view, focus ->
            if (focus) {
                editText.error = null
            } else {
                callbackChange(editText)
            }
        }

        editText.setOnEditorActionListener { textView, actionID, event ->
            if (actionID == EditorInfo.IME_ACTION_DONE) {
                callbackChange(editText)
            }
            return@setOnEditorActionListener false
        }
    }
}