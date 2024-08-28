package com.example.shopclothes.presentation.view.inputs


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import android.widget.TextView.OnEditorActionListener
import com.example.shopclothes.databinding.SliderLayoutBinding
import com.example.shopclothes.utils.fields.SliderField
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.RangeSlider.OnChangeListener

class PriceSliderView: FrameLayout{

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    val block: SliderLayoutBinding
    var eventFromSlider = false

    private var currentValues: Pair<Int, Int> = Pair(0, 1)
    private var minMaxValues: Pair<Int, Int> = Pair(0, 1)
    private var step : Float = 1f

    private var field: SliderField? = null

    private var callbackValues: ((Pair<Int, Int>)->Unit)? = null

    fun setCallback(callback: (Pair<Int, Int>)->Unit){
        callbackValues = callback
    }

    private fun innerCallback(pair: Pair<Int, Int>) {
        this.callbackValues?.invoke(pair)
    }

    fun updateValues(currentValues: Pair<Int, Int>, minMaxValues: Pair<Int, Int>, step : Float){
        this.currentValues = currentValues
        this.minMaxValues = minMaxValues
        this.step = step

        updateSlider()
    }

    private fun updateSlider(){
        val minValue = minMaxValues.first
        val maxValue = minMaxValues.second

        val firstCurrentValue = currentValues.first
        val secondCurrentValue = currentValues.second

        block.slider.apply{
            valueFrom = minValue.toFloat()
            valueTo = maxValue.toFloat()
            values = listOf(
                firstCurrentValue.toFloat(),
                secondCurrentValue.toFloat()
            )
            stepSize = step
        }

        field = SliderField(
            minValue,
            maxValue,
            firstCurrentValue,
            secondCurrentValue
        ){ handler ->
            if(!eventFromSlider){
                block.slider.values = listOf(
                    handler.getFromValue().toFloat(),
                    handler.getToValue().toFloat()
                )
                innerCallback(Pair(handler.getFromValue(), handler.getToValue()))
            }
            block.fromEdit.setText(handler.getFromValue().toString())
            block.toEdit.setText(handler.getToValue().toString())
        }
    }

    init{
        block = SliderLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        block.slider.let {
            block.fromEdit.setOnEditorActionListener(OnEditorActionListener { view, actionID, event ->
                if(actionID == EditorInfo.IME_ACTION_DONE){
                    var value = block.fromEdit.text.toString()
                    if(value.isEmpty()){
                        value = "0"
                    }
                    field?.setFromValue(value.toInt())
                    return@OnEditorActionListener true
                }
                return@OnEditorActionListener false
            })
            block.fromEdit.setOnFocusChangeListener{ _, focus ->
                if(focus.not()){
                    var value = block.fromEdit.text.toString()
                    if(value.isEmpty()){
                        value = "0"
                    }
                    field?.setFromValue(value.toInt())
                }
            }
            block.toEdit.setOnEditorActionListener(OnEditorActionListener { view, actionID, event ->
                if(actionID == EditorInfo.IME_ACTION_DONE){
                    var value = block.toEdit.text.toString()
                    if(value.isEmpty()){
                        value = "0"
                    }
                    field?.setToValue(value.toInt())
                    return@OnEditorActionListener true
                }
                return@OnEditorActionListener false
            })
            block.toEdit.setOnFocusChangeListener{ _, focus ->
                if(focus.not()){
                    var value = block.toEdit.text.toString()
                    if(value.isEmpty()){
                        value = "0"
                    }
                    field?.setToValue(value.toInt())
                }
            }

            block.slider.addOnChangeListener(OnChangeListener { slider, value, fromUser ->
                if(fromUser){
                    field?.setFromValue(block.slider.values[0].toInt())
                    field?.setToValue(block.slider.values[1].toInt())
                }
            })

            block.slider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: RangeSlider) {
                    eventFromSlider = true
                }

                override fun onStopTrackingTouch(slider: RangeSlider) {
                    eventFromSlider = false
                    field?.let {
                        innerCallback(
                            Pair(
                                field!!.getFromValue(),
                                field!!.getToValue()
                            )
                        )
                    }
                }
            })
        }
    }
}