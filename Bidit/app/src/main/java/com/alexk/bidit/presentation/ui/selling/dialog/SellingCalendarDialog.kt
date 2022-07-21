package com.alexk.bidit.presentation.ui.selling.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.databinding.DialogSellingCalendarBinding
import com.alexk.bidit.databinding.DialogSellingTimeBinding
import com.alexk.bidit.domain.entity.selling.SellingCalendarEntity
import com.alexk.bidit.domain.entity.selling.SellingTimeEntity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SellingCalendarDialog(
    private val idxValue: SellingCalendarEntity,
    private val sendEvent: (SellingCalendarEntity) -> Unit
) :
    BottomSheetDialogFragment() {

    private lateinit var binding: DialogSellingCalendarBinding
    private val yearList by lazy { resources.getStringArray(R.array.category_number_picker_year) }
    private val monthList by lazy { resources.getStringArray(R.array.category_number_picker_one_to_twelve) }
    private val dayList by lazy { resources.getStringArray(R.array.category_number_picker_one_to_thirty_one) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_selling_calendar, container, false)
        init()
        initEvent()
        return binding.root
    }

    private fun init() {
        binding.apply {
            npYear.apply {
                value = idxValue.yearIdx
                minValue = 0
                maxValue = yearList.size - 1
                descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
                wrapSelectorWheel = false
                setSelectedTypeface(
                    ResourcesCompat.getFont(
                        requireContext(),
                        R.font.notosans_kr_medium
                    )
                )
                setFormatter { value -> yearList[value] }
            }

            npMonth.apply {
                value = idxValue.monthIdx
                minValue = 0
                maxValue = monthList.size - 1
                wrapSelectorWheel = false
                descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
                setSelectedTypeface(
                    ResourcesCompat.getFont(
                        requireContext(),
                        R.font.notosans_kr_medium
                    )
                )
                setFormatter { value -> monthList[value] }
            }

            npDay.apply {
                value = idxValue.dayIdx
                minValue = 0
                maxValue = dayList.size - 1
                wrapSelectorWheel = false
                descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
                setSelectedTypeface(
                    ResourcesCompat.getFont(
                        requireContext(),
                        R.font.notosans_kr_medium
                    )
                )
                setFormatter { value -> dayList[value] }
            }
        }
    }

    private fun initEvent() {
        binding.apply {
            btnOkay.setOnClickListener {
                val dateInfo = SellingCalendarEntity(
                    npYear.value,
                    npMonth.value+1,
                    npDay.value
                )
                sendEvent(dateInfo)
                dismiss()
            }
        }
    }
}