package com.alexk.bidit.presentation.ui.bid.bidding.alreadyTop

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.databinding.DialogBiddingFailBinding

class BiddingBidAlreadyTopBidDialog(context: Context) : Dialog(context) {
    private lateinit var binding: DialogBiddingFailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_bidding_fail,
            null,
            false
        )
        setContentView(binding.root)

        initCloseButtonEvent()
    }

    private fun initCloseButtonEvent() {
        binding.btnConfirm.setOnClickListener {
            dismiss()
        }
    }
}