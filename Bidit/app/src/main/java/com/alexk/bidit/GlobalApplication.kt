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
import com.sendbird.android.SendBird
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
        initFirebaseSdk()
        initKakoSdk()
        sendBirdInit()
    }


    private fun initKakoSdk(){
        KakaoSdk.init(this,getString(R.string.KAKAO_APP_KEY))
    }

    private fun initFirebaseSdk(){
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.d("Get Fb Token",it)
            TokenManager(this).setPushToken(it)
        }
    }

    fun sendBirdInit(){
        SendBird.init(getString(R.string.SENDBIRD_APP_ID), this)
    }

    //로그인 후 사용해야한다.
//    fun initSendbirdSdk(userId : String, userNickname : String?, userProfileImg : String?){
//        SendbirdUIKit.init(object : SendbirdUIKitAdapter {
//            override fun getAppId(): String {
//                //APP ID
//                return getString(R.string.SENDBIRD_APP_ID)
//            }
//
//            override fun getAccessToken(): String {
//                return ""
//            }
//
//            override fun getUserInfo(): UserInfo {
//                return object : UserInfo{
//                    override fun getUserId(): String {
//                        return userId
//                    }
//
//                    override fun getNickname(): String? {
//                        return userNickname
//                    }
//
//                    override fun getProfileUrl(): String? {
//                        return userProfileImg
//                    }
//                }
//            }
//
//            override fun getInitResultHandler(): InitResultHandler {
//                return object : InitResultHandler{
//                    override fun onInitFailed(e: SendbirdException) {
//                        Log.e(TAG,e.message.toString())
//                    }
//
//                    override fun onInitSucceed() {
//                        Log.e(TAG,"init success")
//                    }
//
//                    override fun onMigrationStarted() {
//                        Log.e(TAG,"migration start")
//                    }
//                }
//            }
//        },this)
//    }

    fun setUserId(userId : Int){
        id = userId
    }
    fun getUserId() : Int{
        return id
    }

    fun setNickname(userNickname : String){
        nickname = userNickname
    }
    fun getNickname() : String {
        return nickname
    }

    companion object {
        lateinit var instance: GlobalApplication
        private var id = 0
        private var nickname = ""
        private const val TAG: String = "LoginActivity..."

        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }
}