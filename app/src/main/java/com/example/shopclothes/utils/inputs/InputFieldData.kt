package com.example.shopclothes.utils.inputs

open class InputFieldData(
    val key : String,
    val value : String? = null,
    val hint : String,
    val type : InputFieldViewFactory.TextInputType = InputFieldViewFactory.TextInputType.TEXT
)