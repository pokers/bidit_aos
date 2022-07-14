package com.alexk.bidit.presentation.ui.bidding.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.common.util.PriceEditTextWatcher
import com.alexk.bidit.common.util.addComma
import com.alexk.bidit.databinding.DialogBiddingBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

//고차함수 (매개변수는 Int, 반환은 Unit(없음))
class BiddingBidDialog(private val bid: (Int) -> Unit) :
    BottomSheetDialogFragment() {

    private lateinit var binding: DialogBiddingBinding
    private var currentPrice = 0
    private var mustOverPrice = 0
    private val bidPrice by lazy { arguments?.getInt("bidPrice") }
    private val inputTextWatcher by lazy { PriceEditTextWatcher(binding.editMerchandisePrice) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_bidding, container, false)
        init()
        initEvent()
        return binding.root
    }

    fun init() {
        currentPrice = arguments?.getInt("currentPrice")!!
        mustOverPrice = arguments?.getInt("currentPrice")!!

        binding.apply {
            PriceEditTextWatcher(editMerchandisePrice)
            editMerchandisePrice.setText(addComma(currentPrice))
            tvMustOverBiddingPrice.text = addComma(currentPrice)
        }
    }

    fun initEvent() {
        binding.apply {
            editMerchandisePrice.addTextChangedListener(inputTextWatcher)

            btnBidding.setOnClickListener {

                currentPrice = inputTextWatcher.getInputPrice()
                Log.d("currentPrice","$currentPrice")

                if (currentPrice <= mustOverPrice) {
                    tvErrorMessage.visibility = View.VISIBLE
                } else {
                    //고차 함수 사용(홈 화면 이동)
                    bid(currentPrice)
                    dismiss()
                }
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
            btnBiddingMinus.setOnClickListener {
                currentPrice -= bidPrice!!
                editMerchandisePrice.setText(addComma(currentPrice))
            }
            btnBiddingPlus.setOnClickListener {
                currentPrice += bidPrice!!
                editMerchandisePrice.setText(addComma(currentPrice))
            }
        }
    }
}