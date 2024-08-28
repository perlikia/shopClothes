package com.example.shopclothes.utils.handlers

import com.example.domain.models.product.ShowcaseProduct
import com.example.domain.usecase.product.GetProductUseCase
import com.example.domain.utils.PagesHandler
import kotlin.math.ceil
import kotlin.math.min

class ProductsCachePagesHandler(
    private val categoryID: String,
    private val productUseCase: GetProductUseCase,
    limitItemOnPage : Int,
    private val filter: ((ShowcaseProduct)->Boolean) = {_ -> true}
) : PagesHandler<ShowcaseProduct>(limitItemOnPage) {
    var items : List<ShowcaseProduct>? = null

    override fun initRequest(): List<ShowcaseProduct> {
        _page = 1
        items = productUseCase.getProductsByCategoryAndFilters(categoryID, filter)
        return items!!
    }

    override fun nextItems(): List<ShowcaseProduct> {
        if(items == null){
            initRequest()
        }
        else{
            _page += 1
        }
        _maxPage = ceil((items!!.size / limitItemOnPage).toDouble()).toInt()
        return sliceItems()
    }

    private fun sliceItems() : List<ShowcaseProduct>{
        val start_index = (_page - 1) * limitItemOnPage
        val end_index = min(start_index + limitItemOnPage, items!!.size)
        return items!!.subList(start_index, end_index)
    }
}