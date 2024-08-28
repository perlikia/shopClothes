package com.example.files

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import org.json.JSONObject
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.StringReader
import java.nio.file.Paths


class FileHandler private constructor(){
    enum class Type(val path: String){
        IMAGE_PRODUCT("images_product"),
        IMAGE_CATEGORY("images_category"),
        USER_DATA("user_data")
    }

    companion object{
        fun writeBitmap(context: Context, filename: String, filetype: Type, content: Drawable) : Boolean{
            val stream = ByteArrayOutputStream()
            val bitmap = (content as BitmapDrawable).bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val bitmapData = stream.toByteArray()
            return writeDataInFile(context, filename, filetype, bitmapData)
        }

        fun writeDataInFile(context: Context, filename: String, filetype: Type, content: ByteArray) : Boolean{
            val filepath = createFilepath(filename, filetype)
            context.openFileOutput(filepath, Context.MODE_PRIVATE).use {
                it.write(content)
            }
            return true
        }

        fun writeContentInFile(context: Context, filename: String, filetype: Type, content: String) : Boolean{
            val filepath = createFilepath(filename, filetype)
            context.openFileOutput(filepath, Context.MODE_PRIVATE).use {
                it.write(content.toByteArray())
            }
            return true
        }

        fun writeJSONFile(context: Context, filename: String, filetype: Type, jsonObject: JSONObject) : Boolean{
            val filepath = createFilepath(filename, filetype)
            context.openFileOutput(filepath, Context.MODE_PRIVATE).use {
                OutputStreamWriter(it).write(jsonObject.toString(0))
            }
            return true
        }

        fun getContentFromFile(context: Context, filename: String, filetype: Type) : String{
            val filepath = createFilepath(filename, filetype)
            context.openFileInput(filepath).use {
                return BufferedReader(InputStreamReader(it)).readText()
            }
        }

        fun getDrawableFromFile(context: Context, filename: String, filetype: Type) : BitmapDrawable{
            val filepath = createFilepath(filename, filetype)
            context.openFileInput(filepath).use {
                return BitmapDrawable(context.resources, it)
            }
        }

        fun fileExists(context: Context, filename: String, filetype: Type) : Boolean{
            val filepath = createFilepath(filename, filetype)
            return context.getFileStreamPath(filepath).exists()
        }

        fun createFilepath(filename: String, filetype: Type) : String{
            val filepath = "${filetype.path}_${filename}"
            return filepath
        }

        fun getFilenameByURL(url: String) : String{
            return Paths.get(url).fileName.toString()
        }
    }

}