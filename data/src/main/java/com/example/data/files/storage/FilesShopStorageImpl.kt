package com.example.data.files.storage

import android.content.res.AssetManager
import com.example.data.files.storage.models.FileShopPoint
import com.example.data.storage.ShopStorage
import com.example.domain.models.shop.ShopPoint
import com.example.files.AssetsHandler
import kotlinx.serialization.json.Json

class FilesShopStorageImpl(private val assetManager: AssetManager) : ShopStorage {

    private fun getPointsByFile() : List<FileShopPoint>{
        val data = AssetsHandler.getFileContent(assetManager, "points_map.json", AssetsHandler.Companion.Type.DATA)
        return Json{
            ignoreUnknownKeys = true
        }.decodeFromString<List<FileShopPoint>>(data)
    }

    override fun getPoints(): List<ShopPoint> {
        return getPointsByFile().map(this::normalizeShopPoint)
    }

    fun normalizeShopPoint(fileShopPoint: FileShopPoint) : ShopPoint{
        return ShopPoint(
            fileShopPoint.id,
            fileShopPoint.name,
            fileShopPoint.lat,
            fileShopPoint.lng,
            fileShopPoint.city,
            fileShopPoint.cityGuid,
            fileShopPoint.address
        )
    }
}