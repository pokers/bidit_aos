package com.alexk.bidit.presentation.ui.bidding.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.databinding.DialogBiddingBoardDeleteBinding
import com.alexk.bidit.databinding.DialogBiddingStatusBinding

class BiddingBoardStatusDialog(context: Context, private val status: Int) : Dialog(context) {

    private lateinit var binding: DialogBiddingStatusBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_bidding_status,
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
            when (status) {
                0 -> {
                    rbReservation.isChecked = true
                }
                1 -> {
                    rbSelling.isChecked = true
                }
                2 -> {
                    rbsellingComplete.isChecked = true
                }
                //판매 종료
                3 -> {

                }
            }
        }
    }

    private fun initEvent() {
        binding.apply {

        }
    }
}
