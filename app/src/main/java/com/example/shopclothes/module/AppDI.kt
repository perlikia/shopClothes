package com.example.shopclothes.module

import android.content.Context
import com.example.shopclothes.presentation.viewmodel.AddressCourierViewModel
import com.example.shopclothes.presentation.viewmodel.AddressSelectViewModel
import com.example.shopclothes.presentation.viewmodel.AuthViewModel
import com.example.shopclothes.presentation.viewmodel.CartViewModel
import com.example.shopclothes.presentation.viewmodel.CatalogRootViewModel
import com.example.shopclothes.presentation.viewmodel.CategoryViewModel
import com.example.shopclothes.presentation.viewmodel.MainContextViewModel
import com.example.shopclothes.presentation.viewmodel.OrderViewModel
import com.example.shopclothes.presentation.viewmodel.ProductPageViewModel
import com.example.shopclothes.presentation.viewmodel.ProductsCategoryViewModel
import com.example.shopclothes.utils.handlers.ImageHandler
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<MainContextViewModel>(){
        MainContextViewModel(
            androidContext().getSharedPreferences("user", Context.MODE_PRIVATE),
            get(), get()
        )
    }
    viewModel<CatalogRootViewModel>(){
        CatalogRootViewModel(get(), get(), get())
    }
    viewModel<AuthViewModel>(){
        AuthViewModel(get())
    }
    viewModel<CategoryViewModel>(){
        CategoryViewModel(get())
    }
    viewModel<ProductsCategoryViewModel>(){
        ProductsCategoryViewModel(get(), get(), get())
    }
    viewModel<ProductPageViewModel>(){
        ProductPageViewModel(get(), get())
    }
    viewModel<OrderViewModel>(){
        OrderViewModel()
    }
    viewModel<CartViewModel>(){
        CartViewModel(get())
    }
    viewModel<AddressSelectViewModel>(){
        AddressSelectViewModel(get())
    }
    viewModel<AddressCourierViewModel>(){
        AddressCourierViewModel(androidContext().assets)
    }
    single {
        ImageHandler(androidApplication())
    }
}