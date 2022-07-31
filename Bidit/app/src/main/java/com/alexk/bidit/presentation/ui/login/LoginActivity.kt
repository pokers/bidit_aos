package com.alexk.bidit.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alexk.bidit.GlobalApplication
import com.alexk.bidit.data.sharedPreference.TokenManager
import com.alexk.bidit.databinding.ActivityLoginBinding
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.common.dialog.LoadingDialog
import com.alexk.bidit.common.dialog.SignOutUserDialog
import com.alexk.bidit.presentation.ui.home.HomeActivity
import com.alexk.bidit.presentation.viewModel.UserViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<UserViewModel>()
    private val callback: (OAuthToken?, Throwable?) -> Unit = { oAuthToken, throwable ->
        if (throwable != null) {
            //로그인 실패
            Log.d("kakao login", "fail")
        } else {
            UserApiClient.instance.me { _, _ ->
                val token = oAuthToken?.accessToken
                //뷰모델로 카카오 로그인 성공 처리
                TokenManager(this).setToken(token!!)
                viewModel.getMyInfo()
            }
            Log.d("kakao login", "success")
        }
    }
    private val loadingDialog by lazy { LoadingDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initEvent()
        observeToken()
    }

    private fun initEvent() {
        binding.btnKakaoLogin.setOnClickListener {
            //카카오톡이 있으면 카카오톡으로
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(
                    this,
                    callback = callback
                )
            }
            //아니면 인터넷으로
            else {
                Toast.makeText(this, "카카오톡을 설치하고 이용해주세요.", Toast.LENGTH_LONG).show()
                finishAffinity()
            }
        }
    }

    private fun observeToken() {
        viewModel.myInfo.observe(this) { response ->
            when (response) {
                //서버 연결 대기중
                is ViewState.Loading -> {
                    Log.d("GET_MY_INFO", "LOADING")
                    loadingDialog.show()
                }
                //로그인 성공
                is ViewState.Success -> {
                    loadingDialog.dismiss()
                    GlobalApplication.id = response.value?.data?.me?.id!!
                    Log.d("login success", "Token: ${TokenManager(this).getToken()}")
                    if (response.value.data?.me?.nickname == null) {
                        viewModel.updateUserInfo("닉네임${GlobalApplication.id}",response.value.data?.me?.kakaoAccount?.profile_image_url)
                    }
                    viewModel.updatePushToken(null, TokenManager(this).getPushToken())
                }
                //탈퇴는 어떻게?
                is ViewState.Error -> {
                    Log.d("GET_MY_INFO", "ERROR")
                    LoadingDialog(this).dismiss()
                    //앱 실행 후, 토큰 재발급 시 오류 발생(메시지 추적해야함)
                    if (response.message == "invalid user") {
                        viewModel.updateUserState(0)
                    }
                }
            }
        }

        viewModel.pushToken.observe(this) { response ->
            when (response) {
                //서버 연결 대기중
                is ViewState.Loading -> {
                    Log.d("UPDATE_PUSH_TOKEN", "LOADING")
                    LoadingDialog(this).show()
                }
                //로그인 성공
                is ViewState.Success -> {
                    LoadingDialog(this).dismiss()
                    Log.d(
                        "pushToken Update",
                        "Success - Token: ${TokenManager(this).getPushToken()}"
                    )
                    startActivity(Intent(this, HomeActivity::class.java))
                }
                is ViewState.Error -> {
                    Log.d("UPDATE_PUSH_TOKEN", "ERROR")
                    LoadingDialog(this).dismiss()
                }
            }
        }
        viewModel.updateUserInfo.observe(this) { response ->
            when (response) {
                //서버 연결 대기중
                is ViewState.Loading -> {
                    Log.d("User Update", "Loading")
                }
                //토큰 확인 성공 -> 홈으로 이동
                is ViewState.Success -> {
                    Log.d(
                        "User Update",
                        "Success - Nickname: ${response.value?.data?.updateUser?.nickname}"
                    )
                }
                //서버 연결 실패(만료) -> 재발급 요청
                is ViewState.Error -> {
                    Log.d("User Update", "failure Update")
                }
            }
        }
    }
}