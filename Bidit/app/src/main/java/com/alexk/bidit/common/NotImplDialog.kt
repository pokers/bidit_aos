package com.alexk.bidit.common

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.alexk.bidit.R
import com.alexk.bidit.databinding.DialogBiddingCancelBinding
import com.alexk.bidit.databinding.DialogNoFeatureMessageBinding
import com.alexk.bidit.presentation.viewModel.BiddingViewModel

class NotImplDialog() : DialogFragment(R.layout.dialog_no_feature_message) {
    private lateinit var binding: DialogNoFeatureMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_bidding_cancel,
            container,
            false
        )
        binding.btnConfirm.setOnClickListener {
            dismiss()
        }
        return binding.root
    }
}