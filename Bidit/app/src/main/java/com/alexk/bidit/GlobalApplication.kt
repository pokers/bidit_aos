package com.alexk.bidit

import android.app.Application
import android.content.Context
import android.util.Log
import com.alexk.bidit.common.util.sharePreference.UserTokenManager
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.KakaoSdk
import com.sendbird.android.SendbirdChat
import com.sendbird.android.exception.SendbirdException
import com.sendbird.android.handler.InitResultHandler
import com.sendbird.android.params.InitParams
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
        initKakaoSdk()
        initSendbird()
    }

    private fun initKakaoSdk() {
        KakaoSdk.init(this, getString(R.string.KAKAO_NATIVE_KEY))
    }

    private fun initFirebaseSdk() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            UserTokenManager.setPushToken(it)
        }
    }


    private fun initSendbird(){
        SendbirdChat.init(
            initParams = InitParams(getString(R.string.SENDBIRD_APP_ID), this, useCaching = false),
            object : InitResultHandler{
                override fun onInitFailed(e: SendbirdException) {

                }

                override fun onInitSucceed() {

                }

                override fun onMigrationStarted() {
                    Log.i(TAG, "onMigrationStarted: init success")
                }
            })
    }

    companion object {
        private const val TAG = "GlobalApplication"

        lateinit var instance: GlobalApplication
        var userId = -1
        var userNickname = ""

        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }
}