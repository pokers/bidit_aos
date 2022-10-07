package com.alexk.bidit.presentation.ui.item.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.common.util.addComma
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
        init()
        initEvent()
    }

    private fun init() {
        binding.apply {
            Log.d("price","$price")
            tvMerchandisePrice.text = addComma(price!!)
        }
    }

    private fun initEvent() {
        binding.apply {
            btnCancel.setOnClickListener {
                dismiss()
            }
            btnChat.setOnClickListener {
                //chatting activity
            }
        }
    }
}