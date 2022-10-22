package com.alexk.bidit.presentation.ui.bid.cancelPayment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.alexk.bidit.R
import com.alexk.bidit.databinding.DialogBiddingCancelBinding
import com.alexk.bidit.common.util.view.ViewState
import com.alexk.bidit.common.dialog.LoadingDialog
import com.alexk.bidit.presentation.viewModel.BiddingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

//취소
@AndroidEntryPoint
@ExperimentalCoroutinesApi
class BiddingBidCancelDialog(private val bidId: Int, val price: Int, private val event: (Unit) -> Unit) :
    DialogFragment(R.layout.dialog_bidding_cancel) {

    private lateinit var binding: DialogBiddingCancelBinding
    private val biddingViewModel by viewModels<BiddingViewModel>()
    private val loadingDialog by lazy { LoadingDialog(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_bidding_cancel,
            container,
            false
        )
        init()
        initEvent()
        return binding.root
    }

    private fun init() {
        observeDeleteStatus()
        binding.apply {

        }
    }

    private fun initEvent() {
        binding.apply {
            btnBidCancel.setOnClickListener {
                biddingViewModel.controlBid(bidId,price,1)
            }
            btnPreviousDisplay.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun observeDeleteStatus() {
        biddingViewModel.biddingInfo.observe(viewLifecycleOwner){response ->
            when(response){
                is ViewState.Loading -> {
                    Log.d("Loading","Cancel bid")
                    loadingDialog.show()
                }
                is ViewState.Success -> {
                    Log.d("Success","Cancel bid")
                    event
                    loadingDialog.dismiss()
                }
                is ViewState.Error -> {
                    Log.d("Error","Cancel bid")
                    loadingDialog.dismiss()
                }
            }
        }
    }
}