package com.alexk.bidit.presentation.ui.selling.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.databinding.DialogSellingTimeBinding
import com.alexk.bidit.domain.entity.selling.SellingTimeEntity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SellingTimePickerDialog(
    private var idxValue: SellingTimeEntity?,
    private val sendEvent: (SellingTimeEntity) -> Unit
) :
    BottomSheetDialogFragment() {

    private lateinit var binding: DialogSellingTimeBinding
    private val dayTimeList by lazy { resources.getStringArray(R.array.category_number_picker_day) }
    private val hourList by lazy { resources.getStringArray(R.array.category_number_picker_zero_to_twelve) }
    private val minuteList by lazy { resources.getStringArray(R.array.category_number_zero_to_fifty_10) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_selling_time, container, false)
        init()
        initEvent()
        return binding.root
    }

    private fun init() {

        if(idxValue == null){
            idxValue = SellingTimeEntity(0,9,2)
        }

        binding.apply {
            npAmPm.apply {
                minValue = 0
                maxValue = dayTimeList.size - 1
                value = idxValue?.hourIdx!!
                descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
                wrapSelectorWheel = false
                setSelectedTypeface(
                    ResourcesCompat.getFont(
                        requireContext(),
                        R.font.notosans_kr_medium
                    )
                )
                setFormatter { value -> dayTimeList[value] }
            }

            npHour.apply {
                minValue = 0
                maxValue = hourList.size - 1
                value = idxValue?.hourIdx!!
                wrapSelectorWheel = false
                descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
                setSelectedTypeface(
                    ResourcesCompat.getFont(
                        requireContext(),
                        R.font.notosans_kr_medium
                    )
                )
                setFormatter { value -> hourList[value] }
            }

            npMinute.apply {
                minValue = 0
                maxValue = minuteList.size - 1
                value = idxValue?.minuteIdx!!
                wrapSelectorWheel = false
                descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
                setSelectedTypeface(
                    ResourcesCompat.getFont(
                        requireContext(),
                        R.font.notosans_kr_medium
                    )
                )
                setFormatter { value -> minuteList[value] }
            }
        }
    }

    private fun initEvent() {
        binding.apply {
            btnOkay.setOnClickListener {
                val dateInfo = SellingTimeEntity(
                    npAmPm.value,
                    npHour.value,
                    npMinute.value
                )
                sendEvent(dateInfo)
                dismiss()
            }
        }
    }
}