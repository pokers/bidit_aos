package com.alexk.bidit.presentation.ui.myPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.databinding.FragmentMyPagePersonalInfoBinding
import com.alexk.bidit.databinding.FragmentMyPageServiceBinding

class MyPageServiceInfoActivity:AppCompatActivity() {
    private lateinit var binding : FragmentMyPageServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_my_page_service)
        setContentView(binding.root)
        init()
        initEvent()
    }

    private fun init(){

    }

    private fun initEvent(){

    }
}