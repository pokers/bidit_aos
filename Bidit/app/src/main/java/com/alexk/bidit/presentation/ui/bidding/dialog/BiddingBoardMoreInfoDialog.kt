package com.alexk.bidit.presentation.ui.bidding.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.common.util.setDialogTransparentBackground
import com.alexk.bidit.databinding.DialogBiddingBoardMoreInfoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BiddingBoardMoreInfoDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogBiddingBoardMoreInfoBinding
    private val itemId by lazy { arguments?.getInt("itemId") }

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
        initEvent()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDialogTransparentBackground()
    }

    private fun initEvent() {
        binding.apply {
            //닫기
            btnClose.setOnClickListener {
                dismiss()
            }
            //수정
            btnModify.setOnClickListener {
                dismiss()
                //
            }
            btnDelete.setOnClickListener {
                //게시글 삭제
                dismiss()
                val dialog =
                    BiddingBoardDeleteDialog(requireContext())
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