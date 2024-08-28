package com.example.shopclothes.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shopclothes.databinding.SelectItemViewBinding

class SelectItemViewFactory {

    data class ViewParams(
        val header : String,
        val content : String
    )

    companion object {
        fun createView(params: ViewParams, layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean, callbackClick: (SelectItemViewBinding)->Unit) : SelectItemViewBinding {
            val block = SelectItemViewBinding.inflate(layoutInflater, parent, attachToParent)
            block.headerText.text = params.header
            block.contentText.text = params.content
            block.root.setOnClickListener{
                callbackClick(block)
            }
            return block
        }
    }
}