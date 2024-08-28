package com.example.shopclothes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.brand.Brand
import com.example.domain.models.category.Category
import com.example.domain.models.product.ShowcaseProduct
import com.example.domain.usecase.category.GetCategoryUseCase
import com.example.domain.usecase.product.GetBrandUseCase
import com.example.domain.usecase.product.GetProductUseCase
import com.example.domain.utils.PagesHandler
import com.example.shopclothes.presentation.view.FiltersViewHandler
import com.example.shopclothes.utils.fields.PredictField
import com.example.shopclothes.utils.fields.ToggleField
import com.example.shopclothes.utils.fields.ToggleList
import com.example.shopclothes.utils.handlers.ProductsCachePagesHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductsCategoryViewModel(
    private val categoryUseCase: GetCategoryUseCase,
    private val productUseCase: GetProductUseCase,
    private val brandsUseCase: GetBrandUseCase
) : ViewModel() {
    val LIMIT_ON_PAGE = 20

    private val _brandsData : MutableLiveData<ToggleList<Brand>> = MutableLiveData(ArrayList())
    val brandsData : LiveData<ToggleList<Brand>> = _brandsData

    private val _categoryData : MutableLiveData<Category> = MutableLiveData()
    val categoryData : LiveData<Category> = _categoryData

    private val _productsListData : MutableLiveData<List<ShowcaseProduct>> = MutableLiveData()
    val productsListData : LiveData<List<ShowcaseProduct>> = _productsListData

    var productsPage : MutableLiveData<PagesHandler<ShowcaseProduct>> = MutableLiveData()

    private val _productsFilter : MutableLiveData<FiltersViewHandler> = MutableLiveData()
    val productsFilter : LiveData<FiltersViewHandler> = _productsFilter

    private var productRequestProcessed = false

    fun initBrands(){
        viewModelScope.launch(Dispatchers.IO) {
            val products = productUseCase.getProductsByCategory(categoryData.value!!.id)
            val list = ArrayList<ToggleField<Brand>>()
            brandsUseCase.getBrandsByProducts(products).forEach{
                list.add(ToggleField(it))
            }
            _brandsData.postValue(list)
        }
    }

    fun updateFilters(){
        val products = productUseCase.getProductsByCategory(_categoryData.value!!.id)
        _productsFilter.value = FiltersViewHandler(products){ handler ->
            requestProductsByFilters()
        }
    }

    fun updateCategoryByID(categoryID: String){
        if(categoryData.value == null || categoryData.value!!.id != categoryID){
            _categoryData.value = categoryUseCase.getCategoryByID(categoryID)
            requestProductsByFilters()
        }
    }

    fun requestProductsByFilters(){
        val handler = _productsFilter.value
        val currentPrices = handler?.getCurrentPrices()
        val currentAttrs = handler?.getCurrentAttrs()
        val selectedBrands = _brandsData.value!!.filter { brand -> brand.isOn() }.map { brand -> brand.data }

        val predictor = PredictField<ShowcaseProduct>{
            addFilter { product ->
                currentPrices?.let {
                    return@addFilter (currentPrices.first..currentPrices.second).contains(product.price)
                }
                return@addFilter true
            }
            addFilter { product ->
                selectedBrands.let {
                    if(selectedBrands.isEmpty()){
                        return@addFilter true
                    }

                    return@addFilter selectedBrands.any { brand -> brand.id == product.brandID }
                }
            }
        }

        productsPage.value = ProductsCachePagesHandler(_categoryData.value!!.id, productUseCase, LIMIT_ON_PAGE, predictor::filtrate)
        updateProducts()

        /*if(
            currentPrices != null
            ||
            (currentAttrs != null && currentAttrs.isEmpty().not())
            ||
            selectedBrands != null
        ){

        }*/
    }

    fun updateProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            val hasNext = productsPage.value!!.hasNext()
            if(hasNext != null && hasNext.not()){
                return@launch
            }
            if(!productRequestProcessed){
                productRequestProcessed = true
                val result = productsPage.value!!.nextItems()
                _productsListData.postValue(result)
                productRequestProcessed = false
            }
        }
    }
}