package com.alexk.bidit.presentation.ui.splash

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alexk.bidit.GlobalApplication
import com.alexk.bidit.data.sharedPreference.TokenManager
import com.alexk.bidit.databinding.ActivitySplashBinding
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.presentation.ui.home.HomeActivity
import com.alexk.bidit.presentation.ui.login.LoginActivity
import com.alexk.bidit.presentation.viewModel.UserViewModel
import com.sendbird.android.SendBird
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

//후에 Splash API 로 변경
@SuppressLint("CustomSplashScreen")
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //현재 카카오 토큰이 존재 -> 토큰 만료 확인
        if (TokenManager(this).getToken().isNotEmpty()) {
            observeMyInfo()
            observePushToken()
            observeUpdateMyInfo()
            viewModel.getMyInfo()
        } else {
            //토큰이 없으면 로그인 페이지로
            val handler = Handler(mainLooper)
            handler.postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
            }, 500)
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

    private fun observeMyInfo(){
        viewModel.myInfo.observe(this) { response ->
            when (response) {
                //서버 연결 대기중
                is ViewState.Loading -> {

                }
                //토큰 확인 성공 -> 홈으로 이동
                is ViewState.Success -> {
                    val me = response.value?.data?.me
                    GlobalApplication.instance.setUserId(me?.id!!)
                    if (response.value.data?.me?.nickname == null) {
                        //닉네임이 없다면 default 닉네임으로 수정
                        val defaultNickname = "닉네임${me.id}"
                        GlobalApplication.instance.setNickname(defaultNickname)
                        viewModel.updateUserInfo(
                            nickname = defaultNickname,
                            profileImg = me.kakaoAccount?.profile_image_url
                        )
                    }
                    //기존 정보 있음
                    else{
                        GlobalApplication.instance.setNickname(me.nickname!!)
                    }
                    //푸쉬 토큰 상시 업데이트
                    viewModel.updatePushToken(null, TokenManager(this).getPushToken())
                    connectToSendBird(GlobalApplication.instance.getUserId().toString(), GlobalApplication.instance.getNickname())
                    //센드버드 init
//                    GlobalApplication.instance.initSendbirdSdk(
//                        userId = GlobalApplication.instance.getUserId().toString(),
//                        userNickname = GlobalApplication.instance.getNickname(),
//                        userProfileImg = me.kakaoAccount?.profile_image_url
//                    )
                }
                //서버 연결 실패(만료) -> 재발급 요청
                is ViewState.Error -> {
                    Log.d("login failure", "expire token")
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        }
    }

    private fun observePushToken(){
        viewModel.pushToken.observe(this) { response ->
            when (response) {
                //서버 연결 대기중
                is ViewState.Loading -> {
                    Log.d("pushToken Update", "Loading")
                }
                //토큰 확인 성공 -> 홈으로 이동
                is ViewState.Success -> {
                    Log.d(
                        "pushToken Update",
                        "Success - Token: ${TokenManager(this).getPushToken()}"
                    )
                    startActivity(Intent(this, HomeActivity::class.java))
                }
                //서버 연결 실패(만료) -> 재발급 요청
                is ViewState.Error -> {
                    Log.d("pushToken Update", "failure token")
                }
            }
        }
    }
    private fun observeUpdateMyInfo() {
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