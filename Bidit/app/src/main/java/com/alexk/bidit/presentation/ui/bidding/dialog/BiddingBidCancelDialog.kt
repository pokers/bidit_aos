package com.alexk.bidit.presentation.ui.bidding.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.alexk.bidit.R
import com.alexk.bidit.databinding.DialogBiddingCancelBinding
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.presentation.viewModel.BiddingViewModel
import com.alexk.bidit.presentation.viewModel.MerchandiseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

//취소
@AndroidEntryPoint
@ExperimentalCoroutinesApi
class BiddingBidCancelDialog(private val bidId: Int, val price: Int, private val event: (Unit) -> Unit) :
    DialogFragment(R.layout.dialog_bidding_cancel) {

    private lateinit var binding: DialogBiddingCancelBinding
    private val biddingViewModel by viewModels<BiddingViewModel>()

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
                }
                is ViewState.Success -> {
                    Log.d("Success","Cancel bid")
                    event
                }
                is ViewState.Error -> {
                    Log.d("Error","Cancel bid")
                }
            }
        }
    }
}