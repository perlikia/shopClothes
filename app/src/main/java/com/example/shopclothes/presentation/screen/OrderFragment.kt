package com.example.shopclothes.presentation.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shopclothes.databinding.CartLayoutBinding
import com.example.shopclothes.presentation.activity.AuthorisationActivity
import com.example.shopclothes.presentation.activity.MainActivity
import com.example.shopclothes.presentation.sheet.OrderBottomSheet
import com.example.shopclothes.presentation.view.blocks.CartItemCardView
import com.example.shopclothes.presentation.viewmodel.CartViewModel
import com.example.shopclothes.presentation.viewmodel.MainContextViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderFragment : Fragment() {
    var viewItems = ArrayList<CartItemCardView>()

    private val model by viewModel<CartViewModel>()
    private val mainModel by lazy{
        requireActivity().getViewModel<MainContextViewModel>()
    }

    private var _binding: CartLayoutBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CartLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as MainActivity

        activity.checkUserAndUpdate { status ->
            when(status){
                AuthorisationActivity.REGISTRATION_STATUS.SUCCESS_USER -> {
                    if(model.cart.isInitialized.not()){
                        model.updateCartByUser(mainModel.getToken()!!)
                    }
                    binding.acceprOtder.setOnClickListener{
                        OrderBottomSheet().show(childFragmentManager, null)
                    }
                }
                AuthorisationActivity.REGISTRATION_STATUS.LATE_USER, AuthorisationActivity.REGISTRATION_STATUS.NONE -> {

                }
            }
        }

        model.cart.observe(viewLifecycleOwner){
            model.cart.value?.let { cartValue ->
                val cartItems = cartValue.items
                if(cartItems.isEmpty()){
                    binding.itemsList.removeAllViews()
                    viewItems = ArrayList()
                }
                else{
                    // Проверка на отсутствие элементов в новом списке, если отсутствуют, то удаляем
                    val deleteSearchList = ArrayList(viewItems)
                    deleteSearchList.forEach{ item ->
                        val searchItem = cartItems.firstOrNull{
                            item.getItemId() == it.id
                        }
                        if(searchItem == null){
                            binding.itemsList.removeView(item)
                            deleteSearchList.remove(item)
                        }
                    }

                    viewItems = deleteSearchList
                    cartItems.forEach{ cartItem ->

                        val searchItem = viewItems.firstOrNull{ viewItem ->
                            viewItem.getItemId() == cartItem.id
                        }

                        if(searchItem == null){
                            val item = CartItemCardView(requireContext()).apply {
                                setCartItem(cartItem)
                                setCallback { newValue ->
                                    model.updateValueItem(
                                        mainModel.getToken()!!,
                                        cartItem,
                                        newValue
                                    )
                                }
                            }
                            item.let{
                                binding.itemsList.addView(it)
                            }
                            viewItems.add(item)
                        }
                        else{
                            searchItem.setCartItem(cartItem)
                        }
                    }
                    Log.d("MyTag", viewItems.size.toString())
                }
            }
        }
    }
}