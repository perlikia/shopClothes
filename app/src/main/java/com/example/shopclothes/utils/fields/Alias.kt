package com.example.shopclothes.utils.fields

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import com.yandex.mapkit.mapview.MapView

typealias IntPair = Pair<Int, Int>
typealias StringPair = Pair<String, String>

class TouchMapView : MapView{
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    this.parent.requestDisallowInterceptTouchEvent(false)
                }

                MotionEvent.ACTION_DOWN -> {
                    this.parent.requestDisallowInterceptTouchEvent(true)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}

fun View.setMargins(
    left: Int,
    top: Int,
    right: Int,
    bottom: Int,
    width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT
){
    layoutParams = MarginLayoutParams(width, height).apply {
        setMargins(left, top, right, bottom)
    }
}