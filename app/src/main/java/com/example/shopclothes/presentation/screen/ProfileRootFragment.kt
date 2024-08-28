package com.example.shopclothes.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.domain.dictionary.Sex
import com.example.domain.models.user.UserPrivateData
import com.example.domain.models.user.UserSizeData
import com.example.shopclothes.databinding.ProfileRootFragmentBinding
import com.example.shopclothes.presentation.sheet.PrivateDataBottomSheet
import com.example.shopclothes.presentation.sheet.SizeDataBottomSheet
import com.example.shopclothes.presentation.viewmodel.MainContextViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.scope.scopeActivity
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ProfileRootFragment : Fragment() {

    val model by lazy {
        requireActivity().getViewModel<MainContextViewModel>()
    }

    private var _binding : ProfileRootFragmentBinding? = null
    val binding : ProfileRootFragmentBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfileRootFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPrivateDataBlock()
        initSizeDataBlock()

    }

    fun initPrivateDataBlock(){
        binding.privateDataContainer.apply {
            setCallback {
                val user = model.user.value
                PrivateDataBottomSheet(user?.privateData){ newData ->
                    model.updateUserPrivateData(newData)
                }.show(childFragmentManager, null)
            }
        }
        model.user.observe(viewLifecycleOwner){
            model.user.value.let{
                binding.privateDataContainer.apply {
                    val user = model.user.value
                    setValues(
                        setOf(
                            Pair("Почта", user?.privateData?.email ?: ""),
                            Pair("Пол", user?.privateData?.sex?.label ?: "")
                        )
                    )
                }
            }
        }
    }

    fun initSizeDataBlock(){
        binding.sizeDataContainer.apply {
            setCallback {
                val sizeData = model.user.value?.sizeData
                SizeDataBottomSheet(
                    sizeData?.sizeFoot,
                    sizeData?.sizeHead,
                    sizeData?.sizeWaist,
                    sizeData?.bodyHeight
                ){ foot, head, waist, height ->
                    model.updateUserSizeData(
                        UserSizeData(
                            head,
                            foot,
                            waist,
                            height
                        )
                    )
                }.show(childFragmentManager, null)
            }
        }

        model.user.observe(viewLifecycleOwner){
            val sizeData = model.user.value?.sizeData
            binding.sizeDataContainer.apply {
                footSize = sizeData?.sizeFoot
                headSize = sizeData?.sizeHead
                waistSize = sizeData?.sizeWaist
                heightSize = sizeData?.bodyHeight
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}