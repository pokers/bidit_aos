package com.alexk.bidit.presentation.ui.bid.cancelPayment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.alexk.bidit.R
import com.alexk.bidit.databinding.DialogBiddingCancelBinding
import com.alexk.bidit.common.util.value.ViewState
import com.alexk.bidit.common.util.setLoadingDialog
import com.alexk.bidit.common.util.value.BidStatus
import com.alexk.bidit.presentation.viewModel.BiddingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

//취소
@AndroidEntryPoint
@ExperimentalCoroutinesApi
class BiddingBidCancelDialog(
    private val deleteBidEvent: () -> Unit
) :
    DialogFragment(R.layout.dialog_bidding_cancel) {

    private lateinit var binding: DialogBiddingCancelBinding

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonEvent()
    }

    private fun initButtonEvent() {
        binding.apply {
            btnBidCancel.setOnClickListener {
                deleteBidEvent.invoke()
            }
            btnPreviousDisplay.setOnClickListener {
                dismiss()
            }
        }
    }
}