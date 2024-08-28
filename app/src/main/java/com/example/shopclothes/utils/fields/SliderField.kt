package com.example.shopclothes.utils.fields

class SliderField(
    private val minValue: Int,
    private val maxValue: Int,
    private var fromValue: Int = minValue,
    private var toValue : Int = maxValue,
    private val callback: (handler: SliderField)->Unit
) {
    init {
        callback(this)
    }

    fun normalizeValue(_value: Int) : Int{
        var value = _value
        if(value > maxValue){
            value = maxValue
        }
        if(value < minValue){
            value = minValue
        }
        return value
    }

    fun getFromValue() : Int{
        return fromValue
    }

    fun getToValue() : Int {
        return toValue
    }

    fun setFromValue(_value: Int){
        var preValue = normalizeValue(_value)
        if(preValue > toValue){
            preValue = toValue
        }
        fromValue = preValue
        callback(this)
    }

    fun setToValue(_value: Int){
        var preValue = normalizeValue(_value)
        if(fromValue > preValue){
            preValue = fromValue
        }
        toValue = preValue
        callback(this)
    }
}