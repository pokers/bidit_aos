package com.alexk.bidit.presentation.ui.bid.bidding.immediatePurchase

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.common.util.TextUtils.addComma
import com.alexk.bidit.common.util.showLongToastMessage
import com.alexk.bidit.databinding.DialogSellingImmediatePurchaseBinding

class BiddingBidImmediatePurchaseDialog(context: Context, private val price: Int?) : Dialog(context) {

    private lateinit var binding: DialogSellingImmediatePurchaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_selling_immediate_purchase,
            null,
            false
        )
        setContentView(binding.root)

        initPriceText()
        addButtonEvent()
    }

    private fun initPriceText() {
        binding.tvMerchandisePrice.text = addComma(price!!)
    }

    private fun addButtonEvent() {
        binding.apply {
            btnCancel.setOnClickListener {
                dismiss()
            }
            btnChat.setOnClickListener {
                context.showLongToastMessage("채팅 개발 중")
            }
        }
    }
}