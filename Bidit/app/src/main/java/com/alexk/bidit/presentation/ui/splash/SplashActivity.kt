package com.alexk.bidit.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alexk.bidit.GlobalApplication
import com.alexk.bidit.common.util.ErrorInvalidToken
import com.alexk.bidit.common.util.ErrorUserNotFound
import com.alexk.bidit.common.util.setLoadingDialog
import com.alexk.bidit.common.util.sharePreference.UserTokenManager
import com.alexk.bidit.databinding.ActivitySplashBinding
import com.alexk.bidit.common.util.view.ViewState
import com.alexk.bidit.domain.entity.user.UserBasicEntity
import com.alexk.bidit.presentation.ui.home.HomeActivity
import com.alexk.bidit.presentation.ui.login.LoginActivity
import com.alexk.bidit.presentation.viewModel.UserViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
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
        checkKakaoTokenExpired()
    }

    //check kakao token expired
    private fun checkKakaoTokenExpired() {
        if (UserTokenManager.getToken().isNotEmpty()) {
            startObserveUserInfo()
        } else {
            startLoginActivity()
        }
    }

    private fun startObserveUserInfo() {
        observeMyInfo()
        observePushToken()
        viewModel.getMyInfo()
    }

    private fun startLoginActivity() {
        val handler = Handler(mainLooper)
        handler.postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
        }, 500)
    }

    private fun observeMyInfo() {
        viewModel.myInfo.observe(this) { response ->
            when (response) {
                is ViewState.Loading -> {
                    setLoadingDialog(true)
                }
                is ViewState.Success -> {
                    setLoadingDialog(false)
                    setUserInfo(userResponse = response.value!!)
                    viewModel.updatePushToken(null, UserTokenManager.getPushToken())
                }
                is ViewState.Error -> {
                    setLoadingDialog(false)
                    throw RuntimeException(ErrorUserNotFound)
                }
            }
        }
    }

    private fun setUserInfo(userResponse: UserBasicEntity) {
        GlobalApplication.userId = userResponse.id!!
        GlobalApplication.userNickname = userResponse.nickname!!
    }

    private fun observePushToken() {
        viewModel.pushToken.observe(this) { response ->
            when (response) {
                is ViewState.Loading -> {
                    setLoadingDialog(true)
                }
                //if push token update successful, start home activity
                is ViewState.Success -> {
                    setLoadingDialog(true)
                    startActivity(Intent(this, HomeActivity::class.java))
                }
                is ViewState.Error -> {
                    setLoadingDialog(false)
                    throw RuntimeException(ErrorInvalidToken)
                }
            }
        }
    }

    companion object {
        private const val TAG = "SplashActivity..."
    }
}