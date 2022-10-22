package com.alexk.bidit

import android.app.Application
import android.content.Context
import android.util.Log
import com.alexk.bidit.common.util.sharePreference.UserTokenManager
import com.alexk.bidit.presentation.ui.login.LoginActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.HiltAndroidApp


//Hilt 를 사용하기위해 반드시 선언
@HiltAndroidApp
class GlobalApplication : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        initFirebaseSdk()
        initKakoSdk()
    }

    private fun initKakoSdk(){
        KakaoSdk.init(this,getString(R.string.KAKAO_NATIVE_KEY))
    }

    private fun initFirebaseSdk(){
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            UserTokenManager.setPushToken(it)
        }
    }

    companion object {
        lateinit var instance: GlobalApplication
        var userId = 0
        var userNickname = ""

        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }
}