package com.alexk.bidit.presentation.ui.myPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.DataBinderMapperImpl
import com.alexk.bidit.R
import com.alexk.bidit.databinding.FragmentMyPagePersonalInfoBinding

class MyPagePersonalInfoManualActivity : AppCompatActivity() {

    private lateinit var binding: FragmentMyPagePersonalInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_my_page_personal_info)
        setContentView(binding.root)
        init()
        initEvent()
    }

    private fun init() {
        binding.apply {
            wvWebview.apply {
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.javaScriptCanOpenWindowsAutomatically = true
                loadUrl("https://biditkr.com/%EC%9D%B4%EC%9A%A9-%EC%95%BD%EA%B4%80-%EB%B0%8F-%EA%B0%9C%EC%9D%B8%EC%A0%95%EB%B3%B4-%EC%B2%98%EB%A6%AC-%EB%B0%A9%EC%B9%A8")
            }
        }
    }

    private fun initEvent() {

    }
}