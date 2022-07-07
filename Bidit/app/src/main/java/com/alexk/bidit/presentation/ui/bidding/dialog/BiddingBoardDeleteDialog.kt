package com.alexk.bidit.presentation.ui.bidding.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.databinding.DialogBiddingBoardDeleteBinding
import com.alexk.bidit.databinding.DialogBiddingBoardDeleteWarningBinding

class BiddingBoardDeleteDialog(context: Context):Dialog(context) {
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
                //게시글 삭제
            }
        }
    }
}