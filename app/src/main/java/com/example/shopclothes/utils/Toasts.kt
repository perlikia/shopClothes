package com.example.shopclothes.utils

import android.content.Context
import android.widget.Toast

class Toasts private constructor(){
    companion object{
        fun showShortToast(context: Context, text: String){
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }
}