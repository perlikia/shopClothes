package com.example.shopclothes

import android.app.Application
import com.example.dadata.DadataHandler
import com.example.shopclothes.module.appModule
import com.example.shopclothes.module.domainModule
import com.yandex.mapkit.MapKitFactory
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(MAP_KEY)
        DadataHandler.setKey(DADATA_KEY)

        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(listOf(
                appModule,
                domainModule
            ))
        }
    }

    companion object {
        val MAP_KEY = "8b006efb-ba87-4dd9-b701-418c6e1dbe30"
        val DADATA_KEY = "75d72fba687c7e2855aee5dcf9e9fb2f7f373361"
    }

}