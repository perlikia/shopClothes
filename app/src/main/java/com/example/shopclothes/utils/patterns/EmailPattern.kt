package com.example.shopclothes.utils.patterns

import java.util.regex.Pattern

class EmailPattern private constructor(){
    companion object {
        val pattern = Pattern.compile("([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)")

        fun isValid(value: String) : Boolean{
            return pattern.matcher(value).find()
        }
    }
}