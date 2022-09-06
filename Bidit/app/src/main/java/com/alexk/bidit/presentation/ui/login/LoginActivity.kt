package com.alexk.bidit.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
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
import com.sendbird.android.SendBird
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.RuntimeException

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
        observeAddUserInfo()
        observeMyInfo()
        observePushToken()
        observeUpdateMyInfo()
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
    private fun connectToSendBird(userID: String, nickname: String) {
        SendBird.connect(userID) { _, e ->
            if (e != null) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            } else {
                SendBird.updateCurrentUserInfo(nickname, null) { error ->
                    if (error != null) {
                        Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                    }
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun observeAddUserInfo(){
        viewModel.addUserInfo.observe(this) { response ->
            when (response) {
                //서버 연결 대기중
                is ViewState.Loading -> {
                    Log.d("GET_MY_INFO", "LOADING")
                    loadingDialog.show()
                }
                //로그인 성공
                is ViewState.Success -> {
                    loadingDialog.dismiss()
                    viewModel.getMyInfo()
                }
                //탈퇴는 어떻게?
                is ViewState.Error -> {
                    throw RuntimeException("Add user Error")
                }
            }
        }
    }
    private fun observePushToken(){
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
    }
    private fun observeUpdateMyInfo(){
        viewModel.updateUserInfo.observe(this) { response ->
            when (response) {
                //서버 연결 대기중
                is ViewState.Loading -> {
                    Log.d("user Update", "Loading")
                }
                //토큰 확인 성공 -> 홈으로 이동
                is ViewState.Success -> {
                    Log.d(
                        "user Update",
                        "Success - Nickname: ${response.value?.data?.updateUser?.nickname}"
                    )
                }
                //서버 연결 실패(만료) -> 재발급 요청
                is ViewState.Error -> {
                    Log.d("user Update", "failure Update")
                }
            }
        }
    }
    private fun observeMyInfo() {
        viewModel.myInfo.observe(this) { response ->
            when (response) {
                is ViewState.Loading -> {
                    Log.d("GET_MY_INFO", "LOADING")
                    loadingDialog.show()
                }
                is ViewState.Success -> {
                    loadingDialog.dismiss()
                    if (response.value?.data?.me?.id == null) {
                        viewModel.addUser()
                    } else {
                        val me = response.value.data?.me
                        GlobalApplication.instance.setUserId(me?.id!!)
                        //닉네임이 없다면 default로 넣어주기
                        if (response.value.data?.me?.nickname == null) {
                            val defaultNickname = "닉네임${GlobalApplication.instance.getUserId()}"
                            GlobalApplication.instance.setNickname(defaultNickname)
                            viewModel.updateUserInfo(
                                defaultNickname,
                                me.kakaoAccount?.profile_image_url
                            )
                        } else {
                            GlobalApplication.instance.setNickname(me.nickname!!)
                        }
                        viewModel.updatePushToken(null, TokenManager(this).getPushToken())
                        connectToSendBird(userID = GlobalApplication.instance.getUserId().toString(), nickname = GlobalApplication.instance.getNickname())
//                        GlobalApplication.instance.initSendbirdSdk(
//                            userId = GlobalApplication.instance.getUserId().toString(),
//                            userNickname = GlobalApplication.instance.getNickname(),
//                            userProfileImg = me.kakaoAccount?.profile_image_url
//                        )
                    }
                }
                is ViewState.Error -> {
                    throw RuntimeException("MyInfo Error")
                }
            }
        }
    }
}