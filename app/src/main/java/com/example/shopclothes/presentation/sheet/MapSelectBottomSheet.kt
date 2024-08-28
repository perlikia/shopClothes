package com.example.shopclothes.presentation.sheet

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioButton
import com.example.shopclothes.databinding.RadioFilterViewLayoutBinding
import com.example.shopclothes.databinding.SelectItemFiltersSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.radiobutton.MaterialRadioButton

class MapSelectBottomSheet(
    private val defaultValue: String?,
    private val label: String?,
    private val values: Set<Pair<String, String>>,
    private val callback: (String?)->Unit
) : BottomSheetDialogFragment() {

    var data: String? = defaultValue

    private var _binding : SelectItemFiltersSheetBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SelectItemFiltersSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sheetLabel.text = label

        val buttons = ArrayList<Pair<String, RadioButton>>()
        values.forEach{ item ->
            val key = item.first
            val placeholder = item.second

            RadioFilterViewLayoutBinding.inflate(layoutInflater, binding.itemsList, true).apply {
                buttons.add(Pair(key, radioBtn))
                if(defaultValue == key){
                    radioBtn.isChecked = true
                }
                radioLabel.text = placeholder
                radioBtn.setOnClickListener{
                    buttons.forEach{ buttonPair ->
                        buttonPair.second.isChecked = false
                    }
                    radioBtn.isChecked = true
                    data = key
                }
            }
        }

        binding.acceptBtn.setOnClickListener{
            callback(data)
            dialog!!.dismiss()
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