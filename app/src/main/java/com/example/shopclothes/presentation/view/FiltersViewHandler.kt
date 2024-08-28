package com.example.shopclothes.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.domain.models.product.AttributeKey
import com.example.domain.models.product.AttributeValue
import com.example.domain.models.product.Product
import com.example.domain.models.product.ShowcaseProduct
import com.example.shopclothes.databinding.FiltersBlockLayoutBinding
import com.example.shopclothes.utils.fields.IntPair
import com.example.shopclothes.utils.fields.ToggleField
import com.example.shopclothes.utils.fields.ToggleList

class FiltersViewHandler(
    private val products: List<ShowcaseProduct>,
    private val callbackChange: (handler: FiltersViewHandler) -> Unit
) {
    enum class Sort(val value: String, val label: String) {
        POPULAR("popular", "Популярное"),
        PRICE_UP("price_up", "По увеличению цены"),
        PRICE_DOWN("price_down", "По уменьшению цены");

        fun getPair() : Pair<String, String>{
            return Pair(value, label)
        }
    }

    // Calculated values
    private val attrFilters = HashMap<AttributeKey, ToggleList<AttributeValue>>()
    private var price : IntPair? = null

    // Current values
    private var currentSortValue = Sort.POPULAR.value
    private var currentPrices : IntPair? = null

    private fun initFilters(){
        generatePriceFilter()
        generateAttrFilters()
    }

    private fun generatePriceFilter(){
        val prices = products.map { product ->
            if(product.price != null) return@map product.price!! else return@map 0
        }
        price = Pair(prices.min(), prices.max())
        if(currentPrices == null){
            currentPrices = price!!.copy()
        }
    }

    private fun generateAttrFilters(){
        products.forEach{
            it.attrs?.let{ attrs ->
                for(attr in attrs){
                    val key = attr.key
                    val values = attrFilters.getOrDefault(key, ArrayList())
                    val isEmpty = values.filter { value ->
                        value.data == attr.value
                    }.isEmpty()
                    if(isEmpty){
                        values.add(ToggleField(attr.value))
                    }
                }
            }
        }
    }

    fun createView(
        layoutInflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean,
        fragmentManager: FragmentManager
    ) : View {
        initFilters()
        val block = FiltersBlockLayoutBinding.inflate(
            layoutInflater,
            parent,
            attachToParent
        )
        val root = block.root

        //Sorting
        /*FilterRadioSelectViewFactory.createView(
            layoutInflater,
            root,
            true,
            FilterRadioSelectViewFactory.ViewParams(
                currentSortValue,
                Pair(
                    "Сортировка",
                    setOf(
                        Sort.POPULAR.getPair(),
                        Sort.PRICE_UP.getPair(),
                        Sort.PRICE_DOWN.getPair()
                    )
                )
            ),
            fragmentManager,
        ){ currentValue ->
            currentSortValue = currentValue
            callbackChange(this@FiltersViewHandler)
        }

        //Price
        PriceSliderViewFactory.createView(
            layoutInflater,
            root,
            true,
            PriceSliderViewFactory.ViewParams(
                currentPrices!!,
                price!!
            )
        ){ setCurrentPrices ->
            currentPrices = setCurrentPrices
            callbackChange(this@FiltersViewHandler)
        }

        //Attributes
        attrFilters.forEach{ attr ->
            GroupFilterViewFactory.createView(
                layoutInflater,
                root,
                true,
                GroupFilterViewFactory.ViewParams(attr.toPair())
            ){ _, _ ->
                callbackChange(this@FiltersViewHandler)
            }
        }*/
        return root
    }

    fun getCurrentPrices() : IntPair? {
        if(currentPrices!!.equals(price)){
            return null
        }
        return currentPrices
    }

    fun getCurrentAttrs() : Map<AttributeKey, List<AttributeValue>>{
        val activeMap = HashMap<AttributeKey, List<AttributeValue>>()
        attrFilters.forEach{ key, values ->
            values.forEach{ value ->
                val list = activeMap.getOrDefault(key, ArrayList())
                if(value.isOn()){
                    (list as ArrayList).add(value.data)
                }
            }
        }
        return activeMap
    }
}