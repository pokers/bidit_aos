package com.alexk.bidit.presentation.ui.bidding

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.common.util.addComma
import com.alexk.bidit.databinding.DialogBiddingBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

//고차함수 (매개변수는 Int, 반환은 Unit(없음))
class BiddingDialog(private val bid: (Int) -> Unit) :
    BottomSheetDialogFragment() {

    private lateinit var binding: DialogBiddingBinding
    private var currentPrice = 0
    private var mustOverPrice = 0
    private val bidPrice by lazy { arguments?.getInt("bidPrice") }
    private var inputPriceText = ""

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
            editMerchandisePrice.setText(addComma(currentPrice))
            tvMustOverBiddingPrice.text = addComma(currentPrice)
        }
    }

    fun initEvent() {
        binding.apply {
            editMerchandisePrice.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    if(editMerchandisePrice.text.toString() != ""){
                        val getEditText = s.toString().toCharArray()
                        var price = ""
                        for(data in getEditText.indices){
                            if(getEditText[data] != ','){
                                price += getEditText[data]
                            }
                        }
                        currentPrice = price.toInt()
                        editMerchandisePrice.setText(addComma(price.toInt()))
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    
                }
            })

            btnBidding.setOnClickListener {
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