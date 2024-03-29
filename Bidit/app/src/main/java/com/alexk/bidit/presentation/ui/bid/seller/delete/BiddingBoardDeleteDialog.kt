package com.alexk.bidit.presentation.ui.bid.seller.delete

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.databinding.DialogBiddingBoardDeleteBinding

class BiddingBoardDeleteDialog(context: Context, private val deleteEvent: () -> Unit):Dialog(context) {
    private lateinit var binding : DialogBiddingBoardDeleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.dialog_bidding_board_delete,null,false)
        setContentView(binding.root)
        initEvent()
    }

    private fun initEvent(){
        binding.apply {
            btnCancel.setOnClickListener {
                dismiss()
            }
            btnDelete.setOnClickListener {
                deleteEvent.invoke()
                dismiss()
            }
        }
    }
}