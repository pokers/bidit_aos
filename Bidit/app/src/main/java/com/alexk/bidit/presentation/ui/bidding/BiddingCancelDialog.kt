package com.alexk.bidit.presentation.ui.bidding
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.common.util.addComma
import com.alexk.bidit.databinding.DialogBiddingCancelBinding
import com.alexk.bidit.databinding.DialogSellingPostImmediatePurchaseBinding

//취소
class BiddingCancelDialog(context: Context, val bidId : Int) : Dialog(context) {

    private lateinit var binding: DialogBiddingCancelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_bidding_cancel,
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
            btnBidCancel.setOnClickListener {
                dismiss()
            }
            btnPreviousDisplay.setOnClickListener {
                //chatting activity
            }
        }
    }
}