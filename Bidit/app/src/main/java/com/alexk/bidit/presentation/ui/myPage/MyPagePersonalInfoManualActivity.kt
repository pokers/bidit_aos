package com.alexk.bidit.presentation.ui.myPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.DataBinderMapperImpl
import com.alexk.bidit.R
import com.alexk.bidit.databinding.FragmentMyPagePersonalInfoBinding

class MyPagePersonalInfoManualActivity :AppCompatActivity(){

    private lateinit var binding : FragmentMyPagePersonalInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_my_page_personal_info)
        setContentView(binding.root)
        init()
        initEvent()
    }

    private fun init(){

    }

    private fun initEvent(){

    }
}