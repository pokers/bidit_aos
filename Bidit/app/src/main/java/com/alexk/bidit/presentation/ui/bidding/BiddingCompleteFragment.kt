package com.alexk.bidit.presentation.ui.bidding

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.alexk.bidit.R
import com.alexk.bidit.common.util.addComma
import com.alexk.bidit.databinding.FragmentBiddingCompleteBinding
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.ui.bidding.dialog.BiddingBidCancelDialog
import com.alexk.bidit.presentation.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
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
                activity?.finishAffinity()
                startActivity(Intent(requireContext(),HomeActivity::class.java))
            }

            btnBidCancle.setOnClickListener {
                val dialog =
                    BiddingBidCancelDialog(args.bid, args.price) {
                        Toast.makeText(requireContext(),"입찰이 취소되었습니다.",Toast.LENGTH_SHORT).show()
                        activity?.finishAffinity()
                        startActivity(Intent(requireContext(),HomeActivity::class.java))
                    }
                dialog.show(childFragmentManager, tag)
            }
        }
    }
}