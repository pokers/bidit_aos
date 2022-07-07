package com.alexk.bidit.presentation.ui.bidding

import android.os.Bundle
import android.view.View
import com.alexk.bidit.R
import com.alexk.bidit.databinding.FragmentBiddingCheckBinding
import com.alexk.bidit.presentation.base.BaseFragment
/* 삭제된 내용, */
class BiddingCheckFragment :
    BaseFragment<FragmentBiddingCheckBinding>(R.layout.fragment_bidding_check),
    View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initEvent()
    }

    override fun init() {
        binding.apply {
            //이미지 받아서 처리해야함
            ivMerchandiseImg
            //가격 받아서 처리해야함
            tvMerchandisePrice
        }
    }

    override fun initEvent() {
        binding.apply {
            btnBidding.setOnClickListener(this@BiddingCheckFragment)
            btnCancel.setOnClickListener(this@BiddingCheckFragment)
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.btnBidding -> {

            }
            binding.btnCancel -> {

            }
        }
    }

}