package com.example.shopclothes.presentation.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.asFlow
import com.example.domain.models.user.FullUser
import com.example.shopclothes.R
import com.example.shopclothes.databinding.ProductPageFragmentBinding
import com.example.shopclothes.presentation.activity.AuthorisationActivity
import com.example.shopclothes.presentation.activity.MainActivity
import com.example.shopclothes.presentation.view.inputs.ToggleView
import com.example.shopclothes.presentation.viewmodel.MainContextViewModel
import com.example.shopclothes.presentation.viewmodel.ProductPageViewModel
import com.example.shopclothes.utils.Toasts
import com.example.shopclothes.utils.fields.setMargins
import com.example.shopclothes.utils.handlers.ImageHandler
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.get

class ProductPageFragment : Fragment() {
    var sizesView = ArrayList<ToggleView>()
    val productID by lazy { getString(R.string.productID) }

    private val mainModel: MainContextViewModel by lazy{
        requireActivity().getViewModel<MainContextViewModel>()
    }
    private val model: ProductPageViewModel by viewModel<ProductPageViewModel>()

    private var _binding : ProductPageFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProductPageFragmentBinding.inflate(inflater)
        if(model.product.isInitialized.not()){
            model.updateProductById(requireArguments().getString(productID)!!)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.product.observe(viewLifecycleOwner){
            updateViews()
            sizesView = ArrayList()
            model.product.value?.let { product ->
                val handler: ImageHandler = get().get()
                product.images.firstOrNull()?.let { image ->
                    handler.setupProductImage(binding.productImage, image.toString())
                }

                product.sizes?.let { sizes ->
                    sizes.forEach{ size ->
                        val currentSize = model.currentSize.value
                        ToggleView(
                            requireContext()
                        ).apply {
                            label = size.size
                            isChecked = size.size == currentSize?.size
                            setMargins(
                                15, 0, 15, 0,
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            setCallback { status ->
                                if(status){
                                    sizesView.forEach{ element ->
                                        element.isChecked = false
                                    }
                                    isChecked = true
                                    model.updateCurrentSize(
                                        size
                                    )
                                }

                                if(sizesView.filter { item ->
                                    item.isChecked
                                }.isEmpty()){
                                    model.updateCurrentSize(
                                        null
                                    )
                                }
                                updateViews()
                            }
                        }.let {
                            binding.sizesList.addView(it)
                            sizesView.add(it)
                        }
                    }
                }
            }
            updateViews()
        }

        binding.addCartBtn.setOnClickListener{
            if(model.currentSize.value == null){
                Toasts.showShortToast(requireContext(), "Выберите размер")
                return@setOnClickListener
            }
            if(binding.addCartBtn.isProgress()){
                return@setOnClickListener
            }
            val activity = requireActivity() as MainActivity
            activity.checkUserAndUpdate { status ->
                when(status){
                    AuthorisationActivity.REGISTRATION_STATUS.SUCCESS_USER -> {
                        binding.addCartBtn.startProgress()
                        model.addProductInCart(
                            mainModel.getToken()!!,
                        ){
                            binding.addCartBtn.stopProgress()
                            Toasts.showShortToast(requireContext(), "Успешное добавление в корзину")
                        }
                    }
                    AuthorisationActivity.REGISTRATION_STATUS.LATE_USER ->{
                        Toasts.showShortToast(requireContext(), "Для добавления в корзину необходима авторизация")
                    }
                    AuthorisationActivity.REGISTRATION_STATUS.NONE -> {
                        Toasts.showShortToast(requireContext(), "Ошибка")
                    }
                }
            }
        }
    }

    fun updateViews(){
        val product = model.product.value
        if(product != null){
            binding.productLabel.text = "${product.category?.name ?: ""} ${product.name}"
            binding.descriptionLabel.text = product.description ?: ""
            binding.addCartBtn.text = "${product.price ?: "-"}₽ (${model.currentSize.value?.size ?: "-"})"
        }
    }
}