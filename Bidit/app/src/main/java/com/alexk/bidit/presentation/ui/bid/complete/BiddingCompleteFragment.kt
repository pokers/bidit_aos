package com.alexk.bidit.presentation.ui.bid.complete

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.alexk.bidit.R
import com.alexk.bidit.common.util.addComma
import com.alexk.bidit.common.util.setLoadingDialog
import com.alexk.bidit.common.util.value.BidStatus
import com.alexk.bidit.common.view.ViewState
import com.alexk.bidit.databinding.FragmentBiddingCompleteBinding
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.ui.bid.cancelPayment.BiddingBidCancelDialog
import com.alexk.bidit.presentation.ui.home.HomeActivity
import com.alexk.bidit.presentation.viewModel.BiddingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class BiddingCompleteFragment :
    BaseFragment<FragmentBiddingCompleteBinding>(R.layout.fragment_bidding_complete) {

    private val args: BiddingCompleteFragmentArgs by navArgs()
    private val bidViewModel by viewModels<BiddingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBidSuccessfulPrice()

        observeBidStatus()

        addBidCancelButtonEvent()
        addBidCompleteButtonEvent()
    }

    private fun initBidSuccessfulPrice() {
        binding.tvMerchandisePrice.text = addComma(args.bidPrice)
    }

    private fun cancelBidComplete() {
        Toast.makeText(requireContext(), "입찰이 취소되었습니다.", Toast.LENGTH_SHORT).show()
        activity?.finishAffinity()
        startActivity(Intent(requireContext(), HomeActivity::class.java))
    }

    private fun addBidCancelButtonEvent() {
        binding.btnBidCancle.setOnClickListener {
            val dialog =
                BiddingBidCancelDialog {
                    bidViewModel.controlBid(args.bidItemId, args.bidPrice, BidStatus.INVALID)
                }
            dialog.show(childFragmentManager, tag)
        }
    }

    private fun addBidCompleteButtonEvent() {
        binding.btnOkay.setOnClickListener {
            activity?.finishAffinity()
            startActivity(Intent(requireContext(), HomeActivity::class.java))
        }
    }

    private fun observeBidStatus() {
        bidViewModel.bidCompleteInfo.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    requireContext().setLoadingDialog(true)
                }
                is ViewState.Success -> {
                    requireContext().setLoadingDialog(false)
                    cancelBidComplete()
                }
                is ViewState.Error -> {
                    requireContext().setLoadingDialog(false)
                }
            }
        }
    }

}