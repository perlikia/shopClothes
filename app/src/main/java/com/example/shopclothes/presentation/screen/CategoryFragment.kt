package com.example.shopclothes.presentation.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.GridLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shopclothes.R
import com.example.shopclothes.databinding.CategoryLayoutBinding
import com.example.shopclothes.presentation.view.blocks.CategoryCardView
import com.example.shopclothes.presentation.viewmodel.CategoryViewModel
import com.example.shopclothes.utils.handlers.ImageHandler
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryFragment : Fragment() {

    val model : CategoryViewModel by viewModel<CategoryViewModel>()

    val categoryBundleKey by lazy { getString(R.string.categoryID) }

    var _binding : CategoryLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CategoryLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.categoryData.observe(viewLifecycleOwner){
            val category = model.categoryData.value
            val imageHandler = get<ImageHandler>()
            binding.categoryLabel.text = category?.name
            category?.imagePath?.let {
                imageHandler.setupCategoryImage(binding.imageBackground, category.imagePath!!)
            }
        }

        model.subcategoriesData.observe(viewLifecycleOwner) {
            binding.categoriesList.removeAllViews()

            model.subcategoriesData.value?.forEachIndexed(){ index, categoryItem ->
                CategoryCardView(requireContext()).apply {
                    category = categoryItem
                    setCallback {
                        val bundle = bundleOf()
                        bundle.putString(getString(R.string.categoryID), categoryItem.id)
                        if(categoryItem.hasProducts == true){
                            findNavController().navigate(R.id.action_productsMove, bundle)
                        }
                        else{
                            findNavController().navigate(R.id.action_subCategoryMove, bundle)
                        }
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
                    binding.categoriesList.addView(it)
                }
            }
        }

        if(!model.initStatusData.value!!){
            model.init()
            model.setCategoryID(arguments?.getString(categoryBundleKey))
            model.updateCategories()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}