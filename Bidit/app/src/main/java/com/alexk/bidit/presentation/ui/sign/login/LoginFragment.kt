package com.alexk.bidit.presentation.ui.sign.login

import android.os.Bundle
import android.view.View
import com.alexk.bidit.R
import com.alexk.bidit.databinding.FragmentLoginBinding
import com.alexk.bidit.presentation.base.BaseFragment

class LoginFragment:BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvLoginTest.setOnClickListener {
            navigate(LoginFragmentDirections.actionLoginFragmentToJoinFragment("데이터 도착"))
        }
    }

    override fun init() {
        TODO("Not yet implemented")
    }

    override fun initEvent() {
        TODO("Not yet implemented")
    }
}