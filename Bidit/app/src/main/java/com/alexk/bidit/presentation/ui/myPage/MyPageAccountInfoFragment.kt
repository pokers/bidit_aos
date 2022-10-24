package com.alexk.bidit.presentation.ui.myPage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.alexk.bidit.R
import com.alexk.bidit.common.util.setLoadingDialog
import com.alexk.bidit.databinding.FragmentMyPageAccountInfoBinding
import com.alexk.bidit.common.util.value.ViewState
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MyPageAccountInfoFragment :
    BaseFragment<FragmentMyPageAccountInfoBinding>(R.layout.fragment_my_page_account_info) {

    private val userViewModel: UserViewModel by viewModels()
    private var userId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initEvent()
    }

    private fun init() {
        observeUserInfo()
        userViewModel.getMyInfo()
    }

    private fun initEvent() {
        binding.apply {
            tvSignOut.setOnClickListener {
                navigate(
                    MyPageAccountInfoFragmentDirections.actionMyPageAccountInfoFragment2ToMyPageSignOutFragment(
                        userId
                    )
                )
            }
            ivBack.setOnClickListener {

            }
        }
    }

    private fun observeUserInfo() {
        userViewModel.myInfo.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    context?.setLoadingDialog(true)
                    Log.d("My Page -> UserInfo", "Loading")
                }
                is ViewState.Success -> {
                    context?.setLoadingDialog(false)
                    Log.d("My Page -> UserInfo", "Success")
                    val result = response.value
                    userId = result?.id!!
                    binding.userBasicInfo = result
                }
                is ViewState.Error -> {
                    context?.setLoadingDialog(false)
                    Log.d("My Page -> UserInfo", "Error")
                }
            }
        }
    }

}