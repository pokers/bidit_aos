package com.alexk.bidit.presentation.ui.category

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.common.util.view.EditTextAutoCommaWatcher
import com.alexk.bidit.databinding.DialogMerchandiseFilterBinding

//취소
class CategoryFilterDialog(context: Context, private val bid: (Int) -> Unit) : Dialog(context) {

    private lateinit var binding: DialogMerchandiseFilterBinding
    private var deliveryType = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_merchandise_filter,
            null,
            false
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(binding.root)
        init()
        initEvent()
    }

    private fun init() {
        binding.apply {

        }
    }

    private fun initEvent() {
        binding.apply {
            btnApply.setOnClickListener {
                var check = true
                var maxImmediatePrice =0
                var minImmediatePrice =0
                var maxStartPrice =0
                var minStartPrice =0
                var startMonth = 0
                var endMonth = 0

                if(editImmediateMaxPrice.text.toString() != ""){
                    maxImmediatePrice = (editImmediateMaxPrice.text.toString().replace(",", "")).toInt()
                }
                if(editImmediateMinPrice.text.toString() != ""){
                    minImmediatePrice = (editImmediateMinPrice.text.toString().replace(",", "")).toInt()
                }
                if(editStartMaxPrice.text.toString() != ""){
                    maxStartPrice = (editStartMaxPrice.text.toString().replace(",", "")).toInt()
                }
                if(editStartMinPrice.text.toString() != ""){
                    minStartPrice = (editStartMinPrice.text.toString().replace(",", "")).toInt()
                }
                if(editUsingStartDate.text.toString() != ""){
                    startMonth = (editUsingStartDate.text.toString().replace(",", "")).toInt()
                }
                if(editUsingEndDate.text.toString() != ""){
                    endMonth = (editUsingEndDate.text.toString().replace(",", "")).toInt()
                }

                if (minImmediatePrice > maxImmediatePrice) {
                    lyImmediateMaxPrice.setBackgroundResource(R.drawable.bg_rect_red_orange_transparent_radius4_stroke1)
                    tvImmediatePurchaseError.visibility = View.VISIBLE
                    check = false
                }

                if (minStartPrice > maxStartPrice) {
                    lyStartMaxPrice.setBackgroundResource(R.drawable.bg_rect_red_orange_transparent_radius4_stroke1)
                    tvStartPriceError.visibility = View.VISIBLE
                    check = false
                }

                if (startMonth > endMonth) {
                    lyUsingEndDate.setBackgroundResource(R.drawable.bg_rect_red_orange_transparent_radius4_stroke1)
                    tvUsingDateError.visibility = View.VISIBLE
                    check = false
                }

                if (check) {
                    //필터링
                    dismiss()
                }
            }

            tvFilterClear.setOnClickListener {
                rgTradeType.clearCheck()
                editUsingEndDate.setText("")
                editUsingStartDate.setText("")
                editImmediateMaxPrice.setText("")
                editImmediateMinPrice.setText("")
                editStartMaxPrice.setText("")
                editStartMinPrice.setText("")
            }

            btnCancel.setOnClickListener {
                dismiss()
            }
            //택배
            btnParcel.setOnClickListener {
                deliveryType = 1
            }
            //직거래
            btnDirectPurchase.setOnClickListener {
                deliveryType = 0
            }

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
}