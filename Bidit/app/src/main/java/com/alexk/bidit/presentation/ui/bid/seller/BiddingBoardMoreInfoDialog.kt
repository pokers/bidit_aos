package com.alexk.bidit.presentation.ui.bid.seller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.common.util.setDialogTransparentBackground
import com.alexk.bidit.common.util.value.PostProcessType
import com.alexk.bidit.databinding.DialogBiddingBoardMoreInfoBinding
import com.alexk.bidit.presentation.ui.bid.seller.delete.BiddingBoardDeleteDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BiddingBoardMoreInfoDialog(private val updateEvent: (PostProcessType) -> Unit) : BottomSheetDialogFragment() {
    private lateinit var binding: DialogBiddingBoardMoreInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_bidding_board_more_info,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonEvent()
        setDialogTransparentBackground()
    }

    private fun setOptionMenuEvent(){

    }

    private fun initButtonEvent() {
        binding.apply {
            btnClose.setOnClickListener {
                dismiss()
            }
            btnModify.setOnClickListener {
                dismiss()
                updateEvent(PostProcessType.MODIFY)
            }
            btnDelete.setOnClickListener {
                dismiss()
                //게시글 삭제
                val dialog =
                    BiddingBoardDeleteDialog(requireContext()) {
                        updateEvent(PostProcessType.DELETE)
                    }
                dialog.setCanceledOnTouchOutside(true)
                dialog.show()
                dialog.window?.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
            }
        }
    }
}