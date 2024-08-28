package com.example.shopclothes.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.GridLayout
import android.widget.ScrollView
import androidx.core.os.bundleOf
import androidx.core.view.marginLeft
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shopclothes.R
import com.example.shopclothes.databinding.CategoryItemsListFragmentBinding
import com.example.shopclothes.presentation.sheet.FilterBottomSheet
import com.example.shopclothes.presentation.view.blocks.ProductCardView
import com.example.shopclothes.presentation.view.inputs.ToggleView
import com.example.shopclothes.presentation.viewmodel.ProductsCategoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsCategoryFragment : Fragment() {
    val productBundleKey by lazy { getString(R.string.productID) }
    val categoryBundleKey by lazy { getString(R.string.categoryID) }
    val model : ProductsCategoryViewModel by viewModel<ProductsCategoryViewModel>()

    private var _binding: CategoryItemsListFragmentBinding? = null
    val binding : CategoryItemsListFragmentBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CategoryItemsListFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPage()
        initProductsList()
        initFilters()
    }

    fun initPage(){
        model.categoryData.observe(viewLifecycleOwner){
            val category = model.categoryData.value
            binding.categoryLabel.text = category?.id
        }
        requireArguments().getString(categoryBundleKey)?.let { model.updateCategoryByID(it) }
    }

    fun initProductsList(){
        binding.scrollContainer.setOnScrollChangeListener { _scrollView, x, y, oldX, oldY ->
            val scrollView = _scrollView!! as ScrollView
            val diff: Int = (scrollView.getChildAt(scrollView.childCount - 1).bottom - (scrollView.height + scrollView.scrollY))

            if (diff <= 175) {
                model.updateProducts()
            }
        }

        model.productsListData.observe(viewLifecycleOwner){
            if(model.productsPage.value?.getPage() == 1){
                binding.productsList.removeAllViews()
            }

            val productsList = model.productsListData.value
            productsList?.forEach{ itemProduct ->
                ProductCardView(requireContext()).apply {
                    product = itemProduct
                    setCallback{
                        val bundle = bundleOf(
                            Pair(productBundleKey, itemProduct.id)
                        )
                        findNavController().navigate(R.id.action_productPageMove, bundle)
                    }
                }.apply {
                    val params = GridLayout.LayoutParams().apply {
                        columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f)
                    }
                    (params as MarginLayoutParams).apply {
                        setMargins(
                            15, 10, 15, 10
                        )
                    }
                    layoutParams = params
                }.let {
                    binding.productsList.addView(it)
                }
            }
        }
    }

    fun initFilters(){
        binding.filtersOpenBtn.setOnClickListener{
            FilterBottomSheet().show(childFragmentManager, "Filters")
        }
        model.brandsData.observe(viewLifecycleOwner){
            binding.brandsList.removeAllViews()
            model.brandsData.value?.forEach{ brand ->
                ToggleView(requireContext()).apply {
                    label = brand.data.name
                    isChecked = brand.isOn()
                    setCallback { status ->
                        brand.setStatus(status)
                        model.requestProductsByFilters()
                    }
                }.let {
                    binding.brandsList.addView(it)
                }
            }
        }
        model.initBrands()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null;
    }

}