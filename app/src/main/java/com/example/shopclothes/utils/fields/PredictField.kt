package com.example.shopclothes.utils.fields

class PredictField<T>(block: PredictField<T>.() -> Unit) {
    private val filters: ArrayList<(T)->Boolean> = ArrayList()

    fun addFilter(filter: (T)->Boolean){
        filters.add(filter)
    }

    fun filtrate(obj: T): Boolean{
        filters.forEach{ filter ->
            val status = filter.invoke(obj)
            if(!status){
                return false
            }
        }
        return true
    }

    init {
        block.invoke(this)
    }

}