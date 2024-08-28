package com.example.shopclothes.module

import com.example.data.files.storage.FilesBrandStorageImpl
import com.example.data.files.storage.FilesCartStorageImpl
import com.example.data.files.storage.FilesCategoryStorageImpl
import com.example.data.files.storage.FilesNewsStorageImpl
import com.example.data.files.storage.FilesProductStorageImpl
import com.example.data.files.storage.FilesShopStorageImpl
import com.example.data.files.storage.FilesSlidesStorageImpl
import com.example.data.files.storage.FilesUserStorageImpl
import com.example.data.repository.BrandRepositoryImpl
import com.example.data.repository.CartRepositoryImpl
import com.example.data.repository.CategoryRepositoryImpl
import com.example.data.repository.NewsRepositoryImpl
import com.example.data.repository.ProductRepositoryImpl
import com.example.data.repository.ShopRepositoryImpl
import com.example.data.repository.SlidesRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.usecase.cart.GetCartUseCase
import com.example.domain.usecase.category.GetCategoryUseCase
import com.example.domain.usecase.category.GetNewsUseCase
import com.example.domain.usecase.category.GetSlidesUseCase
import com.example.domain.usecase.product.GetBrandUseCase
import com.example.domain.usecase.product.GetProductUseCase
import com.example.domain.usecase.shop.GetShopUseCase
import com.example.domain.usecase.user.GetUserUseCase
import com.example.domain.usecase.user.UpdateUserUseCase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val domainModule = module {
    single {
        GetSlidesUseCase(
            SlidesRepositoryImpl(FilesSlidesStorageImpl())
        )
    }

    single {
        GetCategoryUseCase(
            CategoryRepositoryImpl(FilesCategoryStorageImpl(androidApplication()))
        )
    }

    single {
        GetProductUseCase(
            ProductRepositoryImpl(FilesProductStorageImpl(androidApplication()))
        )
    }

    single {
        GetCartUseCase(
            CartRepositoryImpl(FilesCartStorageImpl(androidContext(), FilesUserStorageImpl(androidContext())))
        )
    }

    single{
        GetBrandUseCase(
            BrandRepositoryImpl(FilesBrandStorageImpl(androidApplication().assets))
        )
    }

    single{
        GetNewsUseCase(
            NewsRepositoryImpl(FilesNewsStorageImpl())
        )
    }

    single {
        GetUserUseCase(
            UserRepositoryImpl(FilesUserStorageImpl(androidContext()))
        )
    }

    single {
        UpdateUserUseCase(
            UserRepositoryImpl(FilesUserStorageImpl(androidContext()))
        )
    }

    single {
        GetShopUseCase(
            ShopRepositoryImpl(FilesShopStorageImpl(androidContext().assets))
        )
    }
}
