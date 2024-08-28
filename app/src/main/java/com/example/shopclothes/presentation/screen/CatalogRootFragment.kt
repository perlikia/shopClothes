package com.example.shopclothes.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.shopclothes.R
import com.example.shopclothes.databinding.CatalogRootFragmentBinding
import com.example.shopclothes.presentation.adapter.CatalogRootPagerAdapter
import com.example.shopclothes.presentation.view.blocks.CategoryCardView
import com.example.shopclothes.presentation.view.blocks.NewsCardView
import com.example.shopclothes.presentation.viewmodel.CatalogRootViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel


class CatalogRootFragment : Fragment() {

    private var _binding : CatalogRootFragmentBinding? = null
    private val binding get() = _binding!!

    private val model by viewModel<CatalogRootViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CatalogRootFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPager()
        initCategoriesList()
        initNewsList()
    }

    fun initPager(){
        model.slideData.observe(viewLifecycleOwner) {
            binding.slidePager.adapter = CatalogRootPagerAdapter(model.slideData.value!!)
            TabLayoutMediator(binding.dotsPager, binding.slidePager
            ) { _, _ -> }.attach()
        }
        model.updateSlideData()

        binding.slidePager.registerOnPageChangeCallback(object : OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if(binding.slidePager.currentItem == binding.slidePager.adapter!!.itemCount - 1){
                    binding.rightArrowPager.visibility = View.GONE
                }
                else{
                    binding.rightArrowPager.visibility = View.VISIBLE
                }

                if(binding.slidePager.currentItem == binding.slidePager.left){
                    binding.leftArrowPager.visibility = View.GONE
                }
                else{
                    binding.leftArrowPager.visibility = View.VISIBLE
                }
            }
        })

        binding.leftArrowPager.setOnClickListener{
            if(binding.slidePager.currentItem > 0){
                binding.slidePager.setCurrentItem(binding.slidePager.currentItem - 1,true);
            }
        }

        binding.rightArrowPager.setOnClickListener{
            if(binding.slidePager.currentItem < binding.slidePager.adapter!!.itemCount){
                binding.slidePager.setCurrentItem(binding.slidePager.currentItem + 1,true);
            }
        }
    }

    fun initCategoriesList(){
        model.categoryData.observe(viewLifecycleOwner){
            binding.categoriesList.removeAllViews()
            model.categoryData.value!!.forEach{ categoryItem ->
                CategoryCardView(
                    requireContext()
                ).apply {
                    category = categoryItem
                    setCallback {
                        val bundle = bundleOf()
                        bundle.putString(getString(R.string.categoryID), categoryItem.id)
                        findNavController().navigate(R.id.action_categoryMove, bundle)
                    }
                }.let {
                    binding.categoriesList.addView(it)
                }
            }
        }
        model.updateCategoryData()

        binding.categoryiesMoreBtn.setOnClickListener{
            findNavController().navigate(R.id.action_categoryMove)
        }
    }

    fun initNewsList(){
        model.categoryData.observe(viewLifecycleOwner){
            binding.newsList.removeAllViews()
            model.newsData.value!!.forEach {
                NewsCardView(requireContext()).apply {

                }.let {
                    binding.newsList.addView(it)
                }
            }
        }
        model.updateNewsData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}