package com.example.shopclothes.presentation.view.inputs

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.shopclothes.databinding.SelectDatetimeViewBinding
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class SelectDatetimeView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    private var callbackDate: ((LocalDate)->Unit)? = null
    private var callbackTime: ((LocalTime)->Unit)? = null
    private var currentDate : LocalDate? = null
    private var currentTime : LocalTime? = null

    private val block: SelectDatetimeViewBinding

    init{
        val layoutInflater = LayoutInflater.from(context)

        block = SelectDatetimeViewBinding.inflate(LayoutInflater.from(context), this, true)
        block.dateBlock.setOnClickListener{
            DatePickerDialog(layoutInflater.context).apply {
                setOnDateSetListener {
                        _, year, month, dayOfMonth ->
                    currentDate = LocalDate.of(year, month, dayOfMonth)
                    updateLabels()
                    callbackDate?.invoke(currentDate!!)
                }
                currentDate?.let {
                    datePicker.updateDate(currentDate!!.year, currentDate!!.monthValue, currentDate!!.dayOfMonth)
                }
                datePicker.minDate = Calendar.getInstance().timeInMillis
                show()
            }
        }

        block.timeBlock.setOnClickListener{
            val timeDefault = currentTime ?: LocalTime.now()
            TimePickerDialog(
                layoutInflater.context,
                { view, hour, minute ->
                    currentTime = LocalTime.of(hour, minute)
                    updateLabels()
                    callbackTime?.invoke(currentTime!!)
                },
                timeDefault.hour,
                timeDefault.minute,
                true
            ).apply {
                show()
            }
        }

        updateLabels()
    }

    fun updateLabels(){
        block.dateText.text = currentDate?.format(DateTimeFormatter.ISO_LOCAL_DATE) ?: "-"
        block.timeText.text = currentTime?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: "-"
    }
}