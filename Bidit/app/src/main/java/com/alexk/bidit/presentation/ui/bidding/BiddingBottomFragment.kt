package com.alexk.bidit.presentation.ui.bidding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.databinding.DialogBiddingBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

//고차함수 (매개변수는 Int, 반환은 Unit(없음))
class BiddingBottomFragment(private val onClick: (Int) -> Unit) : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var binding: DialogBiddingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_bidding, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initEvent()
    }

    fun init() {

    }

    fun initEvent() {
        binding.apply {
            btnBidding.setOnClickListener(this@BiddingBottomFragment)
            btnCancel.setOnClickListener(this@BiddingBottomFragment)
            btnBiddingMinus.setOnClickListener(this@BiddingBottomFragment)
            btnBiddingPlus.setOnClickListener(this@BiddingBottomFragment)
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
}