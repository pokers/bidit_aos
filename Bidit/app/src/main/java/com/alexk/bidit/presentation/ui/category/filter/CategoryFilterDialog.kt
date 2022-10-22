package com.alexk.bidit.presentation.ui.category.filter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.common.view.EditTextAutoCommaWatcher
import com.alexk.bidit.databinding.DialogMerchandiseFilterBinding
import com.alexk.bidit.domain.entity.item.category.ItemCategoryRequestEntity
import com.alexk.bidit.type.CursorType

//취소
class CategoryFilterDialog(
    context: Context,
    private val categoryId: Int,
    private val cursorType: CursorType,
    private val bid: (ItemCategoryRequestEntity) -> Unit
) : Dialog(context) {

    private lateinit var binding: DialogMerchandiseFilterBinding

    private var maxImmediatePrice = Int.MAX_VALUE
    private var minImmediatePrice = 0
    private var maxStartPrice = Int.MAX_VALUE
    private var minStartPrice = 0
    private var minUsingTime = 0
    private var maxUsingTime = Int.MAX_VALUE
    private var deliveryType = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_merchandise_filter,
            null,
            false
        )
        setTransparentBackground()
        setContentView(binding.root)

        initEditTextWatcher()
        addSearchButtonEvent()
        addCancelButtonEvent()
        addFilterClearButtonEvent()
    }

    private fun setTransparentBackground() {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun initEditTextWatcher() {
        binding.apply {
            editStartMinPrice.apply {
                addTextChangedListener(EditTextAutoCommaWatcher(this))
            }
            editStartMaxPrice.apply {
                addTextChangedListener(EditTextAutoCommaWatcher(this))
            }
            editImmediateMaxPrice.apply {
                addTextChangedListener(EditTextAutoCommaWatcher(this))
            }
            editImmediateMinPrice.apply {
                addTextChangedListener(EditTextAutoCommaWatcher(this))
            }
        }
    }

    private fun getDeliveryType(): Int {
        binding.run {
            return if (btnParcel.isChecked.xor(btnDirectPurchase.isChecked)) {
                2
            } else if (btnParcel.isChecked) {
                1
            } else {
                0
            }
        }
    }

    private fun addSearchButtonEvent() {
        binding.btnApply.setOnClickListener {
            setFilterData()
            if (validateImmediatePrice() && validateStartPrice() && validateStartMonth()) {
                bid(
                    ItemCategoryRequestEntity(
                        deliveryType,
                        minUsingTime,
                        maxUsingTime,
                        minStartPrice,
                        maxStartPrice,
                        minImmediatePrice,
                        maxImmediatePrice,
                        categoryId,
                        cursorType
                    )
                )
                dismiss()
            }
        }
    }

    private fun addFilterClearButtonEvent() {
        binding.apply {
            tvFilterClear.setOnClickListener {
                btnParcel.isChecked = false
                btnDirectPurchase.isChecked = false

                editUsingEndDate.setText("")
                editUsingStartDate.setText("")
                editImmediateMaxPrice.setText("")
                editImmediateMinPrice.setText("")
                editStartMaxPrice.setText("")
                editStartMinPrice.setText("")

                binding.lyImmediateMaxPrice.setBackgroundResource(R.drawable.bg_rect_sliver_transparent_radius4_stroke1)
                binding.tvImmediatePurchaseError.visibility = View.INVISIBLE

                binding.lyStartMaxPrice.setBackgroundResource(R.drawable.bg_rect_sliver_transparent_radius4_stroke1)
                binding.tvStartPriceError.visibility = View.INVISIBLE

                binding.lyUsingEndDate.setBackgroundResource(R.drawable.bg_rect_sliver_transparent_radius4_stroke1)
                binding.tvUsingDateError.visibility = View.INVISIBLE
            }
        }
    }

    private fun addCancelButtonEvent() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun setFilterData() {
        binding.run {
            if (editImmediateMaxPrice.text.toString() != "") {
                maxImmediatePrice = (editImmediateMaxPrice.text.toString().replace(",", "")).toInt()
            }
            if (editImmediateMinPrice.text.toString() != "") {
                minImmediatePrice = (editImmediateMinPrice.text.toString().replace(",", "")).toInt()
            }
            if (editStartMaxPrice.text.toString() != "") {
                maxStartPrice = (editStartMaxPrice.text.toString().replace(",", "")).toInt()
            }
            if (editStartMinPrice.text.toString() != "") {
                minStartPrice = (editStartMinPrice.text.toString().replace(",", "")).toInt()
            }
            if (editUsingStartDate.text.toString() != "") {
                minUsingTime = (editUsingStartDate.text.toString().replace(",", "")).toInt()
            }
            if (editUsingEndDate.text.toString() != "") {
                maxUsingTime = (editUsingEndDate.text.toString().replace(",", "")).toInt()
            }
        }
    }

    private fun validateImmediatePrice(): Boolean {
        if (minImmediatePrice > maxImmediatePrice) {
            binding.lyImmediateMaxPrice.setBackgroundResource(R.drawable.bg_rect_red_orange_transparent_radius4_stroke1)
            binding.tvImmediatePurchaseError.visibility = View.VISIBLE
            return false
        }
        return true
    }

    private fun validateStartPrice(): Boolean {
        if (minStartPrice > maxStartPrice) {
            binding.lyStartMaxPrice.setBackgroundResource(R.drawable.bg_rect_red_orange_transparent_radius4_stroke1)
            binding.tvStartPriceError.visibility = View.VISIBLE
            return false
        }
        return true
    }

    private fun validateStartMonth(): Boolean {
        if (minUsingTime > maxUsingTime) {
            binding.lyUsingEndDate.setBackgroundResource(R.drawable.bg_rect_red_orange_transparent_radius4_stroke1)
            binding.tvUsingDateError.visibility = View.VISIBLE
            return false
        }
        return true
    }
}