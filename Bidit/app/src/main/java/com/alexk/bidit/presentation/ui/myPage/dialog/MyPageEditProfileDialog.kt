package com.alexk.bidit.presentation.ui.myPage.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.databinding.DialogMyPageProfileMoreInfoBinding
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class MyPageEditProfileDialog(context: Context) : Dialog(context) {

    private lateinit var binding: DialogMyPageProfileMoreInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.dialog_my_page_profile_more_info,
            null,
            false
        )
        setContentView(binding.root)
        init()
        initEvent()
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