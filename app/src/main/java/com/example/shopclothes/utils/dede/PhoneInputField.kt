package com.example.shopclothes.utils.dede

import com.example.shopclothes.databinding.TextInputViewBinding
import com.example.shopclothes.utils.mask.PhoneTextWatcher
import java.util.regex.Pattern

class PhoneInputField(key: String, valueChangeCallback: (Boolean, String) -> Unit
) : RegexInputField(key, valueChangeCallback){
    companion object {
        val pattern = Pattern.compile("\\+7 \\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}")
    }

    override fun isValid(value: String): Boolean {
        return pattern.matcher(value).find()
    }

    override fun callbackAfterCreateView(binding: TextInputViewBinding) {
        super.callbackAfterCreateView(binding)
        binding.editText.addTextChangedListener(PhoneTextWatcher())
    }
}