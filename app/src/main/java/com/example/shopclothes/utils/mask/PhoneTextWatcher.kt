package com.example.shopclothes.utils.mask

import android.text.Editable
import android.text.TextWatcher

class PhoneTextWatcher : TextWatcher {
    var sb: StringBuilder = StringBuilder()
    var ignore: Boolean = false

    private val numPlace = 'X'

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
    }

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
    }

    override fun afterTextChanged(editable: Editable) {
        if (!ignore) {
            removeFormat(editable.toString())
            applyFormat(sb.toString())
            ignore = true
            editable.replace(0, editable.length, sb.toString())
            ignore = false
        }
    }

    private fun removeFormat(text: String) {
        sb.setLength(0)
        for (i in 0 until text.length) {
            val c = text[i]
            if (isNumberChar(c)) {
                sb.append(c)
            }
        }
    }

    private fun applyFormat(text: String) {
        val template = getTemplate()
        sb.setLength(0)
        var i = 0
        var textIndex = 0
        while (i < template.length && textIndex < text.length) {
            if (template[i] == numPlace) {
                sb.append(text[textIndex])
                textIndex++
            } else {
                sb.append(template[i])
            }
            i++
        }
    }

    private fun isNumberChar(c: Char): Boolean {
        return c >= '0' && c <= '9'
    }

    companion object {
        fun getTemplate(): String {
            return "+X (XXX) XXX-XX-XX"
        }

        fun isValidData(text: String) : Boolean{
            return getTemplate().length == text.length
        }
    }
}