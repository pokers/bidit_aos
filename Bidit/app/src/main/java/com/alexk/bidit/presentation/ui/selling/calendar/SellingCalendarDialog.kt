package com.alexk.bidit.presentation.ui.selling.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.databinding.DialogSellingCalendarBinding
import com.alexk.bidit.domain.entity.selling.SellingCalendarEntity
import com.alexk.bidit.presentation.ui.selling.SellingFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ExperimentalCoroutinesApi
class SellingCalendarDialog(
    private val sendEvent: () -> Unit
) :
    BottomSheetDialogFragment() {

    private lateinit var binding: DialogSellingCalendarBinding
    private val yearList by lazy { resources.getStringArray(R.array.category_number_picker_year) }
    private val monthList by lazy { resources.getStringArray(R.array.category_number_picker_one_to_twelve) }
    private val dayList by lazy { resources.getStringArray(R.array.category_number_picker_one_to_thirty_one) }

    private val calendar = Calendar.getInstance()

    private var selectYearIdx = 0
    private var selectMonthIdx = 0
    private var selectDayIdx = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_selling_calendar, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPreviousDateData()
        addEndingDateRegistButton()
    }

    private fun checkPreviousDateData() {
        with(SellingFragment.SELLING_INFO.endDate) {
            if (this == null) {
                getCurrentDate()
            } else {
                selectYearIdx = this.yearIdx
                selectMonthIdx = this.monthIdx
                selectDayIdx = this.dayIdx
            }
        }
        initCustomDatePicker()
    }

    private fun getCurrentDate() {
        val currentYear = calendar.get(Calendar.YEAR)

        //you must be plus 1 because time save string
        val currentMonth = if ((calendar.get(Calendar.MONTH) + 1) < 10) {
            "0" + calendar.get(Calendar.MONTH)
        } else {
            (calendar.get(Calendar.MONTH) + 1).toString()
        }

        val currentDay = if (calendar.get(Calendar.DATE) < 10) {
            "0" + calendar.get(Calendar.DATE)
        } else {
            calendar.get(Calendar.DATE).toString()
        }

        selectYearIdx = yearList.indexOf(currentYear.toString())
        selectMonthIdx = monthList.indexOf(currentMonth)
        selectDayIdx = dayList.indexOf(currentDay)
    }

    private fun initCustomDatePicker() {
        initYearPicker()
        initMonthPicker()
        initDatePicker()
    }

    private fun initYearPicker() {
        binding.npYear.apply {
            minValue = 0
            maxValue = yearList.size - 1
            value = selectYearIdx
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
    }

    private fun initMonthPicker() {
        binding.npMonth.apply {
            minValue = 0
            maxValue = monthList.size - 1
            value = selectMonthIdx
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
    }

    private fun initDatePicker() {
        binding.npDay.apply {
            minValue = 0
            maxValue = dayList.size - 1
            value = selectDayIdx
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

    private fun addEndingDateRegistButton() {
        binding.btnOkay.setOnClickListener {
            SellingFragment.SELLING_INFO.apply {
                this.endDate = SellingCalendarEntity(binding.npYear.value, binding.npMonth.value, binding.npDay.value)
            }
            sendEvent.invoke()
            dismiss()
        }
    }
}