package com.alexk.bidit.presentation.ui.bidding

import android.os.Bundle
import android.view.View
import com.alexk.bidit.R
import com.alexk.bidit.databinding.FragmentBiddingCompleteBinding
import com.alexk.bidit.presentation.base.BaseFragment

class BiddingCompleteFragment :
    BaseFragment<FragmentBiddingCompleteBinding>(R.layout.fragment_bidding_complete),View.OnClickListener {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initEvent()
    }

    override fun init() {
        //입찰 성공을 알려줌
        binding.apply {
            //가격
            tvMerchandisePrice

            //이미지지
           ivMerchandiseImg
        }
    }

    override fun initEvent() {
        binding.apply {
            btnOkay.setOnClickListener(this@BiddingCompleteFragment)
        }
    }

    override fun onClick(view: View?) {
        when(view){
            binding.btnOkay -> {
                //플로우 확인해서 보기
            }
        }
    }
}