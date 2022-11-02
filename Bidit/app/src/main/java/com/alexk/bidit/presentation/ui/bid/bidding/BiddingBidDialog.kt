package com.alexk.bidit.presentation.ui.bid.bidding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.common.util.IntegerUtils.parsePriceTypeToInt
import com.alexk.bidit.common.util.TextUtils.addComma
import com.alexk.bidit.common.util.value.KeyConstants.FRAGMENT_KEY_BID_PRICE
import com.alexk.bidit.common.util.value.KeyConstants.FRAGMENT_KEY_CURRENT_BID_PRICE
import com.alexk.bidit.common.view.EditTextPriceWatcher
import com.alexk.bidit.databinding.DialogBiddingBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BiddingBidDialog(private val bid: (Int) -> Unit) :
    BottomSheetDialogFragment() {

    private lateinit var binding: DialogBiddingBinding

    private var currentPrice = 0
    private var mustOverPrice = 0
    private val bidPrice by lazy { arguments?.getInt(FRAGMENT_KEY_BID_PRICE) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_bidding, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPrice()

        addBidButtonEvent()
        addCancelButtonEvent()
        addBidPriceControlButtonEvent()
    }

    private fun initPrice() {
        currentPrice = arguments?.getInt(FRAGMENT_KEY_CURRENT_BID_PRICE)!!
        mustOverPrice = arguments?.getInt(FRAGMENT_KEY_CURRENT_BID_PRICE)!!

        binding.apply {
            editMerchandisePrice.apply {
                setText(addComma(currentPrice))
                addTextChangedListener(EditTextPriceWatcher(this))
            }
        }
    }

    private fun validateBidPrice() = currentPrice <= mustOverPrice

    private fun addBidPriceControlButtonEvent() {
        binding.apply {
            btnBiddingMinus.setOnClickListener {
                currentPrice -= bidPrice!!
                editMerchandisePrice.setText(addComma(currentPrice))
            }
            btnBiddingPlus.setOnClickListener {
                currentPrice += bidPrice!!
                editMerchandisePrice.setText(addComma(currentPrice))
            }
        }
    }

    private fun addCancelButtonEvent() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun addBidButtonEvent() {
        binding.btnBidding.setOnClickListener {
            currentPrice = parsePriceTypeToInt(binding.editMerchandisePrice.text.toString())
            if (validateBidPrice()) {
                bid(currentPrice)
                dismiss()
            } else {
                binding.tvMustOverBiddingPrice.text = addComma(mustOverPrice)
                binding.tvErrorMessage.visibility = View.VISIBLE
            }
        }
    }


}