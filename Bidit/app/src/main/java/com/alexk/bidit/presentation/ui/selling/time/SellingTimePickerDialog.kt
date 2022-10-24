package com.alexk.bidit.presentation.ui.selling.time

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
import com.alexk.bidit.presentation.ui.selling.SellingActivity.Companion.SELLING_INFO
import com.alexk.bidit.presentation.ui.selling.SellingFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*
import kotlin.math.roundToInt

@ExperimentalCoroutinesApi
class SellingTimePickerDialog(
    private val sendTime: () -> Unit
) :
    BottomSheetDialogFragment() {

    private lateinit var binding: DialogSellingTimeBinding
    private val dayTimeList by lazy { resources.getStringArray(R.array.category_number_picker_day) }
    private val hourList by lazy { resources.getStringArray(R.array.category_number_picker_zero_to_twelve) }
    private val minuteList by lazy { resources.getStringArray(R.array.category_number_zero_to_fifty_10) }

    private var getDayTimeIdx = 0
    private var getHourIdx = 0
    private var getMinuteIdx = 0

    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_selling_time, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPreviousDateData()
        initRegistTimeButtonEvent()
    }

    private fun initCustomTimePicker() {
        initDatePicker()
        initHourPicker()
        initMinutePicker()
    }

    private fun checkPreviousDateData() {
        with(SELLING_INFO.endTime) {
            if (this == null) {
                getCurrentDateIndex()
            } else {
                getDayTimeIdx = this.dateIdx
                getHourIdx = this.hourIdx
                getMinuteIdx = this.minuteIdx
            }
        }
        initCustomTimePicker()
    }

    private fun getCurrentDateIndex() {
        getDayTimeIdx = if (calendar.get(Calendar.HOUR_OF_DAY) > 12) {
            1
        } else {
            0
        }
        val currentHour = if (calendar.get(Calendar.HOUR) > 9) {
            calendar.get(Calendar.HOUR).toString()
        } else {
            "0" + calendar.get(Calendar.HOUR)
        }

        val currentMinute = if (calendar.get(Calendar.MINUTE) > 9) {
            val minute = calendar.get(Calendar.MINUTE).toDouble() / 10
            minute.roundToInt()
        } else {
            "00"
        }
        getHourIdx = hourList.indexOf(currentHour)
        getMinuteIdx = minuteList.indexOf(currentMinute)
    }

    private fun initHourPicker() {
        binding.npHour.apply {
            minValue = 0
            maxValue = hourList.size - 1
            value = getHourIdx
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
    }

    private fun initDatePicker() {
        binding.npAmPm.apply {
            minValue = 0
            maxValue = dayTimeList.size - 1
            value = getDayTimeIdx
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
    }

    private fun initMinutePicker() {
        binding.npMinute.apply {
            minValue = 0
            maxValue = minuteList.size - 1
            value = getMinuteIdx
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

    private fun initRegistTimeButtonEvent() {
        binding.btnOkay.setOnClickListener {
            SELLING_INFO.apply {
                endTime = SellingTimeEntity(
                    binding.npAmPm.value,
                    binding.npHour.value,
                    binding.npMinute.value
                )
            }
            sendTime()
            dismiss()
        }
    }
}