package com.example.shopclothes.utils.handlers

import android.content.Context
import android.util.Log
import android.widget.ImageView
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import com.example.files.FileHandler
import com.example.shopclothes.R

class ImageHandler(private val context: Context){

    private fun setupImage(image: ImageView, url: String, type: FileHandler.Type){
        val filename = FileHandler.getFilenameByURL(url)
        if(FileHandler.fileExists(context, filename, type)){
            image.load(FileHandler.getDrawableFromFile(context, filename, type))
        }
        else{
            val request = ImageRequest.Builder(context).data(url).target{ drawable ->
                FileHandler.writeBitmap(context, filename, type, drawable)
                image.load(drawable)
            }.error(R.drawable.image_product_card).build()
            context.imageLoader.enqueue(request)
        }
    }

    fun setupCategoryImage(image: ImageView, url: String){
        setupImage(image, url, FileHandler.Type.IMAGE_CATEGORY)
    }

    fun setupProductImage(image: ImageView, url: String){
        setupImage(image, url, FileHandler.Type.IMAGE_PRODUCT)
    }
}