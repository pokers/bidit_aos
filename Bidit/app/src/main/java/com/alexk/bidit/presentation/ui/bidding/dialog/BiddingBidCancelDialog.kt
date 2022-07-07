package com.alexk.bidit.presentation.ui.bidding.dialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.databinding.DialogBiddingCancelBinding

//취소
class BiddingBidCancelDialog(context: Context, val bidId : Int) : Dialog(context) {

    private lateinit var binding: DialogBiddingCancelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_bidding_cancel,
            null,
            false
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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
            btnBidCancel.setOnClickListener {
                dismiss()
            }
            btnPreviousDisplay.setOnClickListener {
                //chatting activity
            }
        }
    }
}