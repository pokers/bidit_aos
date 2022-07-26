package com.alexk.bidit.presentation.ui.myPage.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.databinding.DialogMyPageProfileMoreInfoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class MyPageEditProfileDialog() : BottomSheetDialogFragment() {

    private lateinit var binding: DialogMyPageProfileMoreInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,R.style.CustomBottomSheetDialogTransparentBackgroundTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_my_page_profile_more_info, container, false)
        init()
        initEvent()
        return binding.root
    }

    private fun init() {

    }

    private fun initEvent() {
        binding.apply {
            btnAddForAlbum.setOnClickListener {

            }

            btnClose.setOnClickListener {
                dismiss()
            }

            btnAdd.setOnClickListener {

            }

            btnDelete.setOnClickListener {

            }
        }
    }
}