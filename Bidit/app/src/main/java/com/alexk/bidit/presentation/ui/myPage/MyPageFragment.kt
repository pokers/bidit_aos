package com.alexk.bidit.presentation.ui.myPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.alexk.bidit.R
import com.alexk.bidit.databinding.FragmentMyPageBinding
import com.alexk.bidit.common.util.view.ViewState
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val userViewModel by viewModels<UserViewModel>()
    private var imgUrl :String? = null
    private var nickname :String? = null
    private var userId = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUserInfo()
        init()
        initEvent()
    }

    override fun init() {
        binding.apply {

        }
    }

    override fun initEvent() {
        binding.apply {
            tvSellingList.setOnClickListener {
                navigate(MyPageFragmentDirections.actionMyPageFragmentToMyTradeFragment())

            }
            tvAccountInfo.setOnClickListener {
                val intent = Intent(context, MyPageBasicAccountActivity::class.java)
                startActivity(intent)
            }
            tvSettingAlarm.setOnClickListener {
                val intent = Intent(context, MyPageAlarmActivity::class.java)
                intent.putExtra("userId",userId)
                startActivity(intent)
            }
            tvPersonalInfoManual.setOnClickListener {
                startActivity(Intent(context,MyPagePersonalInfoManualActivity::class.java))
            }
            tvServiceManual.setOnClickListener {
                startActivity(Intent(context,MyPageServiceInfoActivity::class.java))
            }
            btnEditProfile.setOnClickListener {
                val intent = Intent(context,MyPageProfileActivity::class.java)
                //Profile, NickName
                intent.putExtra("profile",imgUrl)
                intent.putExtra("nickname",nickname)
                startActivity(intent)
            }
        }
    }

    private fun observeUserInfo() {
        userViewModel.myInfo.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    loadingDialogShow()
                    Log.d("My Page -> UserInfo", "Loading")
                }
                is ViewState.Success -> {
                    loadingDialogDismiss()
                    val result = response.value

                    binding.userBasicEntity = result
                    imgUrl = result?.kakaoAccount?.profileImageUrl
                    nickname = result?.nickname
                    userId = result?.id!!
                    Log.d("My Page -> UserInfo", "Success")
                }
                is ViewState.Error -> {
                    loadingDialogDismiss()
                    Log.d("My Page -> UserInfo", "Error")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        userViewModel.getMyInfo()
    }
}