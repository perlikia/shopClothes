package com.example.shopclothes.utils.mask

import android.text.InputFilter
import android.text.Spanned
import android.util.Log

class InputFilterMinMax(private var min: Int, private var max: Int) : InputFilter {

    fun setPair(pair: Pair<Int, Int>){
        setMin(pair.first)
        setMax(pair.second)
    }

    fun setMin(value: Int){
        min = value
    }

    fun setMax(value: Int){
        max = value
    }

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
            val input = (dest.toString() + source.toString()).toInt()
            if (isInRange(min, max, input)) return null
        } catch (_: NumberFormatException) {
            Log.d("MyTag", "Error")
        }
        return ""
    }

    private fun isInRange(a: Int, b: Int, c: Int): Boolean {
        return if (b > a) c in a..b else c in b..a
    }
}