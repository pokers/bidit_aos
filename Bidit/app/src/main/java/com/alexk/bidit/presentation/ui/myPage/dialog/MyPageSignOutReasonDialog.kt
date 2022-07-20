package com.alexk.bidit.presentation.ui.myPage.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.databinding.DialogBiddingCancelBinding
import com.alexk.bidit.databinding.DialogMyPageSignOutReasonBinding

class MyPageSignOutReasonDialog(context: Context, private val idx: Int, val event: (Int) -> Unit) :
    Dialog(context) {
    private lateinit var binding: DialogMyPageSignOutReasonBinding
    private val reasonList by lazy { context.resources.getStringArray(R.array.category_sign_out_reason) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_my_page_sign_out_reason,
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

            if (idx != -1) {
                rgSignOutReason.check(idx)
            }

            rbDeleteHistory.text = reasonList[0]
            rbManyError.text = reasonList[1]
            rbNotTrust.text = reasonList[2]
            rbNoItem.text = reasonList[3]
            rbUnkindUser.text = reasonList[4]
            rbUsedToAnotherPlatform.text = reasonList[5]
            rbDissatisfactionContent.text = reasonList[6]
            rbNotUsed.text = reasonList[7]
            rbAnotherReason.text = reasonList[8]
        }
    }

    private fun initEvent() {
        binding.apply {
            rbDeleteHistory.setOnClickListener {
                event(0)
                dismiss()
            }
            rbManyError.setOnClickListener {
                event(1)
                dismiss()
            }
            rbNotTrust.setOnClickListener {
                event(2)
                dismiss()
            }
            rbNoItem.setOnClickListener {
                event(3)
                dismiss()
            }
            rbUnkindUser.setOnClickListener {
                event(4)
                dismiss()
            }
            rbUsedToAnotherPlatform.setOnClickListener {
                event(5)
                dismiss()
            }
            rbDissatisfactionContent.setOnClickListener {
                event(6)
                dismiss()
            }
            rbNotUsed.setOnClickListener {
                event(7)
                dismiss()
            }
            rbAnotherReason.setOnClickListener {
                event(8)
                dismiss()
            }

        }
    }
}