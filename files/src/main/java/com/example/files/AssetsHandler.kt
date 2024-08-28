package com.example.files

import android.content.res.AssetManager
import java.io.BufferedReader
import java.io.InputStreamReader

class AssetsHandler private constructor(){
    companion object{
        enum class Type(val path: String) {
            DATA("files_data"),
        }
        fun getFileContent(assetManager: AssetManager, filename: String, type: Type) : String{
            val filepath = createFilePath(filename, type)
            val fileStream = assetManager.open(filepath)
            fileStream.use {
                val reader = InputStreamReader(fileStream)
                val bufferedReader = BufferedReader(reader)
                val builder = StringBuilder()

                bufferedReader.readLines().forEach{ line ->
                    builder.append(line)
                }

                return builder.toString()
            }
        }
        fun createFilePath(filename: String, type: Type) : String{
            return "${type.path}/${filename}"
        }
    }
}