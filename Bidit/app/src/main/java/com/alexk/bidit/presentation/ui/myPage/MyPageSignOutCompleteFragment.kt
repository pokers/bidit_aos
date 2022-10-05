package com.alexk.bidit.presentation.ui.myPage

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alexk.bidit.R
import com.alexk.bidit.databinding.FragmentMyPageSignOutCompleteBinding
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.ui.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MyPageSignOutCompleteFragment:BaseFragment<FragmentMyPageSignOutCompleteBinding>(R.layout.fragment_my_page_sign_out_complete) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
    }
    private fun initEvent() {
        binding.btnBackToLogin.setOnClickListener {
            activity?.finishAffinity()
            startActivity(Intent(context, SplashActivity::class.java))
        }
    }
}