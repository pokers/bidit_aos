package com.alexk.bidit.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alexk.bidit.GlobalApplication
import com.alexk.bidit.data.sharedPreference.TokenManager
import com.alexk.bidit.databinding.ActivitySplashBinding
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.presentation.ui.home.HomeActivity
import com.alexk.bidit.presentation.ui.login.LoginActivity
import com.alexk.bidit.presentation.viewModel.UserViewModel
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
            observeToken()
            viewModel.getMyInfo()
        } else {
            //토큰이 없으면 로그인 페이지로
            val handler = Handler(mainLooper)
            handler.postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
            }, 1500)
        }
    }

    private fun observeToken() {
        viewModel.id.observe(this) { response ->
            when (response) {
                //서버 연결 대기중
                is ViewState.Loading -> {

                }
                //토큰 확인 성공 -> 홈으로 이동
                is ViewState.Success -> {
                    Log.d("login success", "Token: ${TokenManager(this).getToken()}")
                    //내 id 기억하기
                    GlobalApplication.id = response.value?.data?.me?.id!!
                    viewModel.updatePushToken(TokenManager(this).getPushToken())
                }
                //서버 연결 실패(만료) -> 재발급 요청
                is ViewState.Error -> {
                    Log.d("login failure", "expire token")
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        }
        viewModel.pushToken.observe(this) { response ->
            when (response) {
                //서버 연결 대기중
                is ViewState.Loading -> {

                }
                //토큰 확인 성공 -> 홈으로 이동
                is ViewState.Success -> {
                    Log.d("pushToken Update", "Token: ${TokenManager(this).getPushToken()}")
                    startActivity(Intent(this, HomeActivity::class.java))
                }
                //서버 연결 실패(만료) -> 재발급 요청
                is ViewState.Error -> {
                    Log.d("pushToken Update", "failure token")
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        }
    }
}