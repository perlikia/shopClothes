package com.example.shopclothes.presentation.sheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.shopclothes.databinding.SizeDataSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SizeDataBottomSheet(
    sizeFoot: Int?,
    sizeHead: Int?,
    sizeWaist: Int?,
    bodyHeight: Int?,
    val callbackChange: (foot: Int?, head: Int?, waist: Int?, height: Int?) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding : SizeDataSheetBinding? = null
    val binding get() = _binding!!

    var currentFoot: Int? = sizeFoot
    var currentHead: Int? = sizeHead
    var currentWaist: Int? = sizeWaist
    var currentHeight: Int? = bodyHeight

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SizeDataSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.footInput.setText(currentFoot?.toString())
        binding.headInput.setText(currentHead?.toString())
        binding.waistInput.setText(currentWaist?.toString())
        binding.heightInput.setText(currentHeight?.toString())

        binding.acceptBtn.setOnClickListener{
            if(validationFields()){
                callbackChange(
                    currentFoot,
                    currentHead,
                    currentWaist,
                    currentHeight
                )
                dismiss()
            }
        }
    }

    fun validationFields(): Boolean{
        currentFoot = binding.footInput.text?.toString()?.toIntOrNull()
        currentHead = binding.headInput.text?.toString()?.toIntOrNull()
        currentWaist = binding.waistInput.text?.toString()?.toIntOrNull()
        currentHeight = binding.heightInput.text?.toString()?.toIntOrNull()

        return true
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