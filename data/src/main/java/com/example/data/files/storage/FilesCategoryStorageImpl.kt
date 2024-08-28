package com.example.data.files.storage

import android.content.Context
import android.content.res.AssetManager
import com.example.data.CacheData
import com.example.data.storage.CategoryStorage
import com.example.domain.models.category.Category
import com.example.domain.models.product.ShowcaseProduct
import com.example.data.files.storage.models.FileCategory as FileStorageCategory
import kotlinx.serialization.json.Json

class FilesCategoryStorageImpl(val context: Context) : CategoryStorage {

    companion object {
        val cacheData = CacheData<List<Category>>()
    }

    fun getCategoriesList() : List<FileStorageCategory>{
        val content = com.example.files.AssetsHandler.getFileContent(context.assets, "categories.json", com.example.files.AssetsHandler.Companion.Type.DATA)
        val list = Json{
            ignoreUnknownKeys = true
        }.decodeFromString<ArrayList<FileStorageCategory>>(content)
        return list
    }

    override fun getCategories(predicate: (Category) -> Boolean): List<Category> {
        if(cacheData.hasData().not()){
            cacheData.setData(getCategoriesList().map(this::normalizeCategory))
        }
        return cacheData.getData()!!.filter(predicate)
    }

    private fun normalizeCategory(storageCategory : FileStorageCategory) : Category{
        return Category(
            id = storageCategory.id.toString(),
            name = storageCategory.title,
            parent = storageCategory.parent?.toString(),
            hasProducts = FilesProductStorageImpl(context).getProducts(ShowcaseProduct::class.java) { predicate ->
                predicate.categoryID == storageCategory.id.toString()
            }.isNotEmpty()
        )
    }
}