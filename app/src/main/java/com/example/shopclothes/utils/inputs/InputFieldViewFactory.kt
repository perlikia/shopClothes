package com.example.shopclothes.utils.inputs

import android.text.InputType
import android.text.method.DigitsKeyListener
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import com.example.shopclothes.databinding.TextInputViewBinding
import com.example.shopclothes.utils.fields.StringPair
import com.example.shopclothes.utils.mask.PhoneTextWatcher
import java.util.regex.Pattern

class InputFieldViewFactory{
    enum class TextInputType{
        PHONE,
        EMAIL,
        TEXT,
        SELECT
    }

    data class ViewParams(
        val type: TextInputType = TextInputType.TEXT,
        val hint: String
    )

    companion object{
        fun createView(
            viewParams: ViewParams,
            layoutInflater: LayoutInflater,
            parent: ViewGroup?,
            attachToParent: Boolean,
            dataChangedCallback: (validData: Boolean, data: String, editText: EditText) -> Unit
        ) : View {

            var root : View? = null

            when(viewParams.type){
                TextInputType.PHONE -> {
                    val block = createInputView(layoutInflater, parent, attachToParent, viewParams.hint, dataChangedCallback){ editText ->
                        val value = editText.text.toString()
                        return@createInputView PhoneTextWatcher.isValidData(value)
                    }.apply {
                        editText.inputType = InputType.TYPE_CLASS_PHONE
                        editText.keyListener = DigitsKeyListener.getInstance("1234567890+-() ");
                        editText.addTextChangedListener(PhoneTextWatcher())
                    }
                    root = block.root
                }
                TextInputType.EMAIL -> {
                    val pattern = Pattern.compile("([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)")

                    val block = createInputView(layoutInflater, parent, attachToParent, viewParams.hint, dataChangedCallback){ editText ->
                        val value = editText.text.toString()
                        return@createInputView pattern.matcher(value).find()
                    }.apply {
                        editText.inputType = InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS
                    }

                    root = block.root
                }
                TextInputType.TEXT -> {
                    val block = createInputView(layoutInflater, parent, attachToParent, viewParams.hint, dataChangedCallback) {
                        true
                    }
                    root = block.root
                }

                TextInputType.SELECT -> {
                    /*root = createSelectView(
                        layoutInflater, parent, attachToParent
                    )*/
                    TODO()
                }
            }

            return root
        }

        fun createInputView(
            layoutInflater: LayoutInflater,
            parent: ViewGroup?,
            attachToParent: Boolean,
            hint: String,
            dataChangedCallback: (validData: Boolean, data: String, editText: EditText) -> Unit,
            isValid : (editText: EditText) -> Boolean,
        ) : TextInputViewBinding{
            val block = TextInputViewBinding.inflate(layoutInflater, parent, attachToParent)
            val editText = block.editText

            val callback = fun (){
                val data = editText.text.toString()

                if(isValid(editText).not()){
                    editText.error = "Неправильный ввод"
                }
                dataChangedCallback(isValid(editText), data, editText)
            }

            editText.onFocusChangeListener = OnFocusChangeListener { view, focus ->
                if(focus){
                    editText.error = null
                }
                else{
                    callback()
                }
            }

            editText.setOnEditorActionListener { textView, actionID, event ->
                if (actionID == EditorInfo.IME_ACTION_DONE) {
                    callback()
                }
                return@setOnEditorActionListener false
            }

            editText.hint = hint

            return block
        }

        /*fun createSelectView(
            layoutInflater: LayoutInflater,
            parent: ViewGroup?,
            attachToParent: Boolean,
            defaultValue: String,
            pair: Pair<String, Set<StringPair>>,
            fragmentManager: FragmentManager
        ) : View{
            return FilterRadioSelectViewFactory.createView(
                layoutInflater,
                parent,
                attachToParent,
                FilterRadioSelectViewFactory.ViewParams(
                    defaultValue,
                    pair
                ),
                fragmentManager
            ){

            }
        }*/
    }
}