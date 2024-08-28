package com.example.data

class CacheData<T> {
    private var data: T? = null

    fun setData(obj: T){
        data = obj
    }

    fun getData(): T?{
        return data
    }

    fun hasData(): Boolean{
        return data != null
    }
}