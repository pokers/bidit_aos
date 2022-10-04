package com.alexk.bidit.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alexk.bidit.GlobalApplication
import com.alexk.bidit.common.util.sharePreference.UserTokenManager
import com.alexk.bidit.databinding.ActivityLoginBinding
import com.alexk.bidit.common.util.view.ViewState
import com.alexk.bidit.common.util.ErrorInvalidToken
import com.alexk.bidit.common.util.ErrorUserNotFound
import com.alexk.bidit.common.util.setLoadingDialog
import com.alexk.bidit.domain.entity.user.UserBasicEntity
import com.alexk.bidit.presentation.ui.home.HomeActivity
import com.alexk.bidit.presentation.viewModel.UserViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.RuntimeException

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<UserViewModel>()
    private val kakaoLoginAccessCallback = initKakaoLoginCallback()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initKakaoLoginButton()

        observeAddUserInfo()
        observeUserInfo()
        observeUserPushToken()
    }

    private fun initKakaoLoginCallback(): (OAuthToken?, Throwable?) -> Unit =
        { oAuthToken, throwable ->
            if (throwable != null) {
                Log.e(TAG, "initKakaoLoginCallback: ${throwable.message}")
            } else {
                Log.d(TAG, "initKakaoLoginCallback: login success")
                UserApiClient.instance.me { _, _ ->
                    UserTokenManager.setToken(oAuthToken?.accessToken!!)
                    viewModel.getMyInfo()
                }
            }
        }

    private fun initKakaoLoginButton() {
        binding.btnKakaoLogin.setOnClickListener {
            //if kakao talk login available...
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(
                    this,
                    callback = kakaoLoginAccessCallback
                )
            } else {
                UserApiClient.instance.loginWithKakaoAccount(
                    this,
                    callback = kakaoLoginAccessCallback
                )
            }
        }
    }

    private fun setDefaultUserNickName(userId: Int) {
        val defaultNickname = "닉네임$userId"
        GlobalApplication.userNickname = defaultNickname
    }

    private fun setUserInfoFisrtTime(userResponse: UserBasicEntity) {
        GlobalApplication.userId = userResponse.id!!

        //닉네임이 없다면 default로 넣어주기
        if (userResponse.nickname == null) {
            setDefaultUserNickName(userResponse.id!!)
            viewModel.updateUserNickNameAndProfileImg(
                GlobalApplication.userNickname,
                userResponse.kakaoAccount?.profileImageUrl
            )
        } else {
            GlobalApplication.userNickname = userResponse.nickname!!
        }
    }

    private fun observeUserInfo() {
        viewModel.myInfo.observe(this) { response ->
            when (response) {
                is ViewState.Loading -> {
                    setLoadingDialog(true)
                }
                is ViewState.Success -> {
                    setLoadingDialog(false)
                    val userResponse = response.value
                    //first login -> required add user
                    if (userResponse?.id == null) {
                        viewModel.addUser()
                    } else {
                        setUserInfoFisrtTime(userResponse = userResponse)
                        viewModel.updatePushToken(null, UserTokenManager.getPushToken())
                    }
                }
                is ViewState.Error -> {
                    setLoadingDialog(false)
                    throw RuntimeException(ErrorUserNotFound)
                }
            }
        }
    }

    private fun observeAddUserInfo() {
        viewModel.addUserInfo.observe(this) { response ->
            when (response) {
                is ViewState.Loading -> {
                    setLoadingDialog(true)
                }
                //if user login first time, have to require addUser
                is ViewState.Success -> {
                    setLoadingDialog(false)
                    viewModel.getMyInfo()
                }
                is ViewState.Error -> {
                    setLoadingDialog(false)
                    throw RuntimeException(ErrorUserNotFound)
                }
            }
        }
    }

    private fun observeUserPushToken() {
        viewModel.pushToken.observe(this) { response ->
            when (response) {
                is ViewState.Loading -> {
                    setLoadingDialog(true)
                }
                //if push token update successful, start home activity
                is ViewState.Success -> {
                    setLoadingDialog(false)
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
        private const val TAG = "LoginActivity..."
    }
}