package com.example.shopclothes.presentation.sheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.domain.dictionary.Sex
import com.example.domain.models.user.UserPrivateData
import com.example.shopclothes.databinding.PrivateDataSheetBinding
import com.example.shopclothes.presentation.view.inputs.FilterRadioSelectView
import com.example.shopclothes.utils.fields.StringPair
import com.example.shopclothes.utils.patterns.EmailPattern
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PrivateDataBottomSheet(
    private val privateData: UserPrivateData?,
    private val callbackChange: (UserPrivateData) -> Unit
) : BottomSheetDialogFragment() {

    var _binding : PrivateDataSheetBinding? = null
    val binding get() = _binding!!
    var currentSexValue: String? = null
    var currentEmail: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PrivateDataSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.acceptBtn.setOnClickListener{
            if(validationFields()){
                callbackChange(
                    UserPrivateData(
                        email = currentEmail,
                        sex = Sex.convertToSex(currentSexValue)
                    )
                )
                dismiss()
            }
        }
        currentEmail = privateData?.email
        binding.emailInput.setText(currentEmail)

        currentSexValue = privateData?.sex?.key ?: Sex.None.key
        binding.selectSexContainer.apply {
            fragmentManager = parentFragmentManager
            setValues(
                currentSexValue,
                Sex.toMap().map { element -> Pair(element.key, element.value)}.toSet()
            )
            setCallback { currentValue ->
                currentSexValue = currentValue
            }
        }
    }

    fun validationFields() : Boolean{
        currentEmail = binding.emailInput.text.toString()
        var notErrors = true
        if(currentEmail!!.isEmpty().not() && EmailPattern.isValid(currentEmail!!).not()){
            binding.emailInput.error = "Неправильная почта"
            notErrors = false
        }
        return notErrors
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