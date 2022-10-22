package com.alexk.bidit.presentation.ui.bid.seller.status

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.common.util.value.ItemStatus
import com.alexk.bidit.databinding.DialogBiddingStatusBinding


class BiddingItemStatusDialog(
    context: Context,
    private val status: ItemStatus,
    private val cPrice: Int?,
    private val statusEvent: (ItemStatus) -> Unit
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

        selectRadioButtonByStatus()
        addStatusChangeEvent()
    }

    private fun selectRadioButtonByStatus() {
        when (status) {
            ItemStatus.REGISTED, ItemStatus.ONGOING -> {
                binding.rbSelling.isChecked = true
            }
            ItemStatus.SOLD -> {
                binding.rbReservation.isChecked = true
            }
            ItemStatus.END, ItemStatus.CANCEL -> {
                binding.rbsellingComplete.isChecked = true
            }
        }

    }

    private fun addStatusChangeEvent() {
        binding.apply {
            rgStatus.setOnCheckedChangeListener { _, checkedId ->
                when(checkedId){
                    R.id.rb_selling -> {
                        if (cPrice == null) {
                            statusEvent(ItemStatus.REGISTED)
                        } else {
                            statusEvent(ItemStatus.ONGOING)
                        }
                        dismiss()
                    }
                    R.id.rb_reservation -> {
                        statusEvent(ItemStatus.SOLD)
                        dismiss()
                    }
                    R.id.rbselling_complete -> {
                        statusEvent(ItemStatus.END)
                        dismiss()
                    }
                    R.id.rbselling_cancel -> {
                        statusEvent(ItemStatus.CANCEL)
                        dismiss()
                    }
                }
            }
        }
    }
}
