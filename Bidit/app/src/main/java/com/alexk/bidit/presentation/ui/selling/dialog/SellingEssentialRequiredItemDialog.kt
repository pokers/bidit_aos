package com.alexk.bidit.presentation.ui.selling.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.common.util.addComma
import com.alexk.bidit.databinding.DialogSellingImmediatePurchaseBinding
import com.alexk.bidit.databinding.DialogSellingRequiredContentBinding

class SellingEssentialRequiredItemDialog(context: Context) : Dialog(context) {
    private lateinit var binding: DialogSellingRequiredContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_selling_required_content,
            null,
            false
        )
        setContentView(binding.root)

        addBackButtonEvent()
    }

    private fun addBackButtonEvent() {
        binding.btnConfirm.apply {
            setOnClickListener {
                dismiss()
            }
        }
    }
}