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
import com.alexk.bidit.databinding.DialogBiddingBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

//고차함수 (매개변수는 Int, 반환은 Unit(없음))
class BiddingDialog(private val onClick: (Int) -> Unit) :
    BottomSheetDialogFragment(), View.OnClickListener, TextWatcher {

    private lateinit var binding: DialogBiddingBinding

    var price = 0

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
        price = arguments?.getInt("price")!!
    }

    fun initEvent() {
        binding.apply {
            btnBidding.setOnClickListener(this@BiddingDialog)
            btnCancel.setOnClickListener(this@BiddingDialog)
            btnBiddingMinus.setOnClickListener(this@BiddingDialog)
            btnBiddingPlus.setOnClickListener(this@BiddingDialog)

        }
    }

    override fun onClick(view: View?) {
        when (view) {

            //입찰
            binding.btnBidding -> {
                //고차 함수 사용
                onClick(1)
                dismiss()
            }

            //취소
            binding.btnCancel -> {
                dismiss()
            }

            //마이너스
            binding.btnBiddingMinus -> {

            }

            //플러스
            binding.btnBiddingPlus -> {

            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        Log.d("before", s.toString())
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        Log.d("change", s.toString())
    }

    override fun afterTextChanged(s: Editable?) {
        Log.d("after", s.toString())
    }
}