package com.alexk.bidit.presentation.ui.bidding.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.databinding.DialogBiddingBoardDeleteWarningBinding

//판매글 삭제 불가능
class BiddingBoardDeleteWarningDialog(context: Context) : Dialog(context) {
    private lateinit var binding: DialogBiddingBoardDeleteWarningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_bidding_board_delete_warning,
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