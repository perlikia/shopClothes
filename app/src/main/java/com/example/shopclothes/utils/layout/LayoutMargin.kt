package com.example.shopclothes.utils.layout

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TableLayout
import android.widget.TableRow

final class LayoutMargin private constructor(){
    companion object {
        fun addMargins(rootLayoutParams: LayoutParams, topMargin: Int = 0, bottomMargin: Int = 0, rightMargin: Int = 0, leftMargin: Int = 0) : ViewGroup.LayoutParams{
            val marginParams = rootLayoutParams as MarginLayoutParams
            marginParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin)
            return rootLayoutParams
        }
    }
}