package com.example.data.files.storage

import android.content.Context
import android.util.Log
import com.example.data.CacheData
import com.example.data.storage.ProductStorage
import com.example.domain.models.product.FullProduct
import com.example.domain.models.product.Product
import com.example.domain.models.product.ShowcaseProduct
import com.example.domain.models.product.Size
import kotlinx.serialization.json.Json
import java.net.URI
import com.example.data.files.storage.models.FileProduct as FileStorageProduct

class FilesProductStorageImpl(private val context: Context) : ProductStorage {
    companion object {
        private var cacheData = CacheData<ArrayList<FileStorageProduct>>()
        private var normalizeCacheDataMap: HashMap<String, List<Product>> = HashMap()
    }

    fun getProductsList() : List<FileStorageProduct>{
        if(cacheData.hasData().not()){
            val content = com.example.files.AssetsHandler.getFileContent(context.assets, "products.json", com.example.files.AssetsHandler.Companion.Type.DATA)
            cacheData.setData(Json{
                ignoreUnknownKeys = true
            }.decodeFromString<ArrayList<FileStorageProduct>>(content))
        }
        return cacheData.getData()!!
    }

    override fun <T : Product> getProducts(productClass: Class<T>, predicate: (T) -> Boolean): List<T> {
        if(normalizeCacheDataMap.containsKey(productClass.name).not()){
            val data = getProductsList().map { normalizeProduct(productClass, it) }
            normalizeCacheDataMap.set(productClass.name, data)
        }
        return normalizeCacheDataMap.get(productClass.name)!!.map { item -> item as T }.filter(predicate)
    }

    private fun <T: Product> normalizeProduct(
        shapeClass: Class<T>,
        storageProduct : FileStorageProduct
    ) : T {
        when(shapeClass){
            ShowcaseProduct::class.java -> {
                return ShowcaseProduct(
                    id = storageProduct.id,
                    categoryID = storageProduct.category.toString(),
                    brandID = storageProduct.brand?.brand_id.toString(),
                    images = storageProduct.photos.map { item -> URI.create(item.url) },
                    price = storageProduct.price.toInt()
                ) as T
            }
            FullProduct::class.java -> {
                val brandManager = FilesBrandStorageImpl(context.assets)
                val categoryManager = FilesCategoryStorageImpl(context)
                return FullProduct(
                    id = storageProduct.id,
                    sizes = storageProduct.sizes?.let {
                        storageProduct.sizes.map { item ->
                            Size(
                                item.size,
                            )
                        }
                    },
                    brand = brandManager.getBrands(){ it.id == storageProduct.brand?.brand_id.toString() }.firstOrNull(),
                    category = categoryManager.getCategories { it.id == storageProduct.category.toString() }.firstOrNull(),
                    images = storageProduct.photos.map { item -> URI.create(item.url) },
                    price = storageProduct.price.toInt()
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown class")
    }

}