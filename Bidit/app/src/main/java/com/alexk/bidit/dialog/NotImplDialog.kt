package com.alexk.bidit.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.alexk.bidit.R
import com.alexk.bidit.databinding.DialogBiddingCancelBinding
import com.alexk.bidit.databinding.DialogNoFeatureMessageBinding
import com.alexk.bidit.presentation.viewModel.BiddingViewModel

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