package com.example.data.files.storage

import android.content.res.AssetManager
import com.example.data.CacheData
import com.example.data.storage.BrandStorage
import com.example.domain.models.brand.Brand
import com.example.data.files.storage.models.FileBrand as FileStorageBrand
import kotlinx.serialization.json.Json

class FilesBrandStorageImpl(
    private val assetManager: AssetManager
) : BrandStorage {

    companion object {
        private val cacheData = CacheData<List<Brand>>()
    }

    private fun getBrandsList() : List<FileStorageBrand>{
        val content = com.example.files.AssetsHandler.getFileContent(assetManager, "brands.json", com.example.files.AssetsHandler.Companion.Type.DATA)
        val list = Json{
            ignoreUnknownKeys = true
        }.decodeFromString<ArrayList<FileStorageBrand>>(content)
        return list
    }

    override fun getBrands(predicate: (Brand) -> Boolean): List<Brand> {
        if(cacheData.hasData().not()){
            cacheData.setData(
                getBrandsList().map {
                    normalizeBrand(it)
                }
            )
        }
        return cacheData.getData()!!.filter(predicate)
    }

    private fun normalizeBrand(storageCategory : FileStorageBrand) : Brand {
        return Brand(
            id = storageCategory.id.toString(),
            name = storageCategory.name
        )
    }

}