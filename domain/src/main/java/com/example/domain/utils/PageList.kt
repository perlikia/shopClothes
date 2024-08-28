package com.example.domain.utils

abstract class PagesHandler<T>(protected val limitItemOnPage : Int){
    protected var _page = 1
    protected var _maxPage : Int? = null

    fun hasMax() : Boolean{
        return _maxPage != null
    }

    fun hasNext() : Boolean?{
        if(hasMax()){
            return _page < _maxPage!!
        }
        return null
    }

    fun getMaxPage() : Int?{
        return _maxPage
    }

    fun getPage() : Int{
        return _page
    }

    abstract fun initRequest() : List<T>
    abstract fun nextItems() : List<T>
}