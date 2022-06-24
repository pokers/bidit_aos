package com.alexk.bidit.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.alexk.bidit.databinding.ActivitySplashBinding
import com.alexk.bidit.presentation.ui.home.HomeActivity
import com.alexk.bidit.presentation.ui.login.LoginActivity


//후에 Splash API 로 변경
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //초가 아니고 세션이 확인된다면 넘어가기
        val handler = Handler(mainLooper)
        handler.postDelayed({
            //일단 로그인을 강제로, 나중에 확인
            finish()
            startActivity(Intent(this, HomeActivity::class.java))
        }, 2000)
    }
}