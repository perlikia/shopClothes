package com.example.shopclothes.utils.inputs

class SelectInputFieldData(
    key: String,
    value: String? = null,
    hint: String,
    val values: Map<String, String>
) : InputFieldData(
    key,
    value,
    hint,
    InputFieldViewFactory.TextInputType.SELECT
)