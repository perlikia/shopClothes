package com.example.shopclothes.utils.fields

data class ToggleField<T>(val data : T, private var on : Boolean = false){
    fun toggle() : Boolean {
        on = !on
        return on
    }

    fun setStatus(status: Boolean){
        this.on = status
    }

    fun isOn() : Boolean{
        return on
    }
}