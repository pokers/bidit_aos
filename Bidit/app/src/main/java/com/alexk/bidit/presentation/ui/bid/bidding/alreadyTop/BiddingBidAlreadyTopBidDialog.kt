package com.alexk.bidit.presentation.ui.bid.bidding.alreadyTop

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.databinding.DialogBiddingFailBinding

//판매글 삭제 불가능
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
        init()
        initEvent()
    }

    private fun init() {
        binding.apply {

        }
    }

    private fun initEvent() {
        binding.apply {
            btnConfirm.setOnClickListener {
                dismiss()
            }
        }
    }
}