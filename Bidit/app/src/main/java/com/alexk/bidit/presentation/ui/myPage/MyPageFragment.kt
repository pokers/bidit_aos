package com.alexk.bidit.presentation.ui.myPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.alexk.bidit.R
import com.alexk.bidit.databinding.FragmentMyPageBinding
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.domain.entity.user.UserBasicInfoEntity
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val userViewModel by viewModels<UserViewModel>()
    private var imgUrl = ""
    private var nickname = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUserInfo()
        init()
        initEvent()
    }

    override fun init() {
        userViewModel.getMyInfo()
        binding.apply {

        }
    }

    override fun initEvent() {
        binding.apply {
            tvAccountInfo.setOnClickListener {
                val intent = Intent(context, MyPageBasicAccountActivity::class.java)
                startActivity(intent)
            }
            tvSettingAlarm.setOnClickListener {
                val intent = Intent(context, MyPageAlarmActivity::class.java)
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
                    Log.d("My Page -> UserInfo", "Success")
//                    val result = response.value?.data
//                    if(result?.me?.kakaoAccount == null){
//                        Log.d("My Page -> KakaoInfo", "No info")
//                    }
//                    else{
//                        nickname = result.me.kakaoAccount.nickname!!
//                        imgUrl = result.me.kakaoAccount.profile_image_url!!
//                        binding.userInfo = result
//                    }
                }
                is ViewState.Error -> {
                    loadingDialogDismiss()
                    Log.d("My Page -> UserInfo", "Error")
                }
            }
        }
    }
}