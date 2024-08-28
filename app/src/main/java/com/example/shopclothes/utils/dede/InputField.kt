package com.example.shopclothes.utils.dede

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class InputField(
    val key: String
) {
    abstract fun createView(layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) : View
}