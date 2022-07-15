package com.alexk.bidit.presentation.ui.bidding.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.alexk.bidit.R
import com.alexk.bidit.databinding.DialogBiddingBoardDeleteBinding
import com.alexk.bidit.databinding.DialogBiddingStatusBinding
import com.alexk.bidit.presentation.viewModel.MerchandiseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


class BiddingBoardStatusDialog(
    context: Context,
    private val status: Int,
    private val statusEvent: (Int) -> Unit
) : Dialog(context) {

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
            }
        }
    }

    private fun initEvent() {
        binding.apply {
            rbReservation.setOnClickListener {
                statusEvent(0)
                dismiss()
            }
            rbSelling.setOnClickListener {
                statusEvent(1)
                dismiss()
            }
            rbsellingComplete.setOnClickListener {
                statusEvent(2)
                dismiss()
            }
        }
    }
}
