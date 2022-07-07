package com.alexk.bidit.presentation.ui.bidding

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.navigation.fragment.navArgs
import com.alexk.bidit.R
import com.alexk.bidit.common.util.addComma
import com.alexk.bidit.databinding.FragmentBiddingCompleteBinding
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.ui.bidding.dialog.BiddingBidCancelDialog

class BiddingCompleteFragment :
    BaseFragment<FragmentBiddingCompleteBinding>(R.layout.fragment_bidding_complete) {

    private val args: BiddingCompleteFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initEvent()
    }

    override fun init() {
        //입찰 성공을 알려줌
        binding.apply {
            //가격
            tvMerchandisePrice.text = addComma(args.price)
        }
    }

    override fun initEvent() {
        binding.apply {
            btnOkay.setOnClickListener {
                //확인 -> 어디로 가는지?
            }
            btnBidCancle.setOnClickListener {
                val dialog =
                    BiddingBidCancelDialog(requireContext(), args.bid)
                dialog.setCanceledOnTouchOutside(true)
                dialog.show()
                dialog.window?.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
            }
        }
    }
}