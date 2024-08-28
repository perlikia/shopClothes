package com.example.shopclothes.presentation.sheet

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.MediatorLiveData
import com.example.shopclothes.databinding.OrderBottomSheetBinding
import com.example.shopclothes.databinding.RadioFilterViewLayoutBinding
import com.example.shopclothes.presentation.viewmodel.MainContextViewModel
import com.example.shopclothes.presentation.viewmodel.OrderViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderBottomSheet : BottomSheetDialogFragment() {
    val model by viewModel<OrderViewModel>()
    val mainModel by lazy {
        requireActivity().getViewModel<MainContextViewModel>()
    }

    var _binding : OrderBottomSheetBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OrderBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttons = ArrayList<RadioButton>()
        listOf(
            OrderViewModel.Type.Courier,
            OrderViewModel.Type.Pickup
        ).forEach{ item ->
            RadioFilterViewLayoutBinding.inflate(layoutInflater, binding.tabRadioGroup, true).apply {
                radioBtn.isChecked = false
                radioLabel.text = item.placeholder
                fun callback(){
                    buttons.forEach{ btn ->
                        btn.isChecked = false
                    }
                    radioBtn.isChecked = true
                    model.setTabType(item)
                }
                root.setOnClickListener{
                    callback()
                }
                radioBtn.setOnClickListener{
                    callback()
                }
                buttons.add(radioBtn)
                if(model.tabType.value == item){
                    callback()
                }
            }
        }
        model.tabType.observe(this){
            updateViews()
        }

        if(model.recipient.isInitialized.not()){
            mainModel.user.value?.privateData?.let {
                model.setRecipient(it)
            }
        }
        binding.recipientSelector.setCallback {
            PrivateDataBottomSheet(model.recipient.value){ newData ->
                model.setRecipient(newData)
            }.show(childFragmentManager, null)
        }
        model.recipient.observe(this){
            updateViews()
        }

        binding.addressSelector.setCallback {
            when(model.tabType.value) {
                OrderViewModel.Type.Pickup -> {
                    AddressSelectBottomSheet{ shopPoint ->
                        model.setShopPoint(shopPoint)
                    }.show(childFragmentManager, null)
                }
                OrderViewModel.Type.Courier -> {
                    AddressCourierBottomSheet{ userAddress ->
                        model.setUserAddress(userAddress)
                    }.show(childFragmentManager, null)
                }
                null -> TODO()
            }
        }

        model.shopPointAddress.observe(this){
            updateViews()
        }
        model.userSelectAddress.observe(this){
            updateViews()
        }
    }

    fun updateViews(){
        val receiverText = model.recipient.value?.sex?.label
        binding.recipientSelector.apply {
            header = "Получатель"
            text = receiverText ?: "-"
        }
        when(model.tabType.value){
            OrderViewModel.Type.Pickup -> {
                binding.datetimeContainer.visibility = View.GONE
                binding.courierCallContainer.visibility = View.GONE
                binding.courierDoorContainer.visibility = View.GONE

                binding.addressSelector.apply {
                    header = "Магазин"
                    text = model.shopPointAddress.value?.address ?: "-"
                }
            }
            OrderViewModel.Type.Courier -> {
                binding.datetimeContainer.visibility = View.VISIBLE
                binding.courierCallContainer.visibility = View.VISIBLE
                binding.courierDoorContainer.visibility = View.VISIBLE

                var address = "-"
                model.userSelectAddress.value?.let {
                    address = "${it.street} ${it.house} ${it.entrance} ${it.apartment}"
                }

                binding.addressSelector.apply {
                    header = "Адрес доставки"
                    text = address
                }
            }
            null -> TODO()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { view ->
                val behaviour = BottomSheetBehavior.from(view)
                setupFullHeight(view)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }
}