package com.alexk.bidit

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import com.alexk.bidit.data.sharedPreference.TokenManager
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp


//Hilt 를 사용하기위해 반드시 선언
@HiltAndroidApp
class GlobalApplication : Application() {

    init {
        instance = this
    }

    //이때 의존성 주입이 이루어진다.
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this,getString(R.string.kakao_app_key))
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.d("fbToken",it)
            TokenManager(this).setPushToken(it)
        }
    }

    companion object {
        lateinit var instance: GlobalApplication
            private set

        const val baseUrl = "https://wypcpelqdbhlxgrexisgez7vba.appsync-api.ap-northeast-2.amazonaws.com/graphql/"

        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }
}