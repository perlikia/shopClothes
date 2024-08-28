package com.example.shopclothes.utils.dede

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

class EmailInputField(key: String, valueChangeCallback: (Boolean, String) -> Unit
) : RegexInputField(key, valueChangeCallback){
    companion object {
        val pattern = Pattern.compile("([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)")
    }

    override fun isValid(value: String): Boolean {
        return pattern.matcher(value).find()
    }

}