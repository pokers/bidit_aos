package com.alexk.bidit.common.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.alexk.bidit.databinding.DialogNoFeatureMessageBinding

class NotImplDialog(context : Context) : Dialog(context) {
    private lateinit var binding: DialogNoFeatureMessageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogNoFeatureMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.2f)
    }
}