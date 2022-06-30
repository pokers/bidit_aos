package com.alexk.bidit.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alexk.bidit.GetMyInfoQuery
import com.alexk.bidit.data.sharedPreference.TokenManager
import com.alexk.bidit.databinding.ActivitySplashBinding
import com.alexk.bidit.di.ApolloClient
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
            if (TokenManager(this).getToken().isNotEmpty()) {
                lifecycleScope.launchWhenResumed {
//                    val response =
//                        ApolloClient.provideApolloClient().query(GetMyInfoQuery()).execute()
//                    //카카오 토큰 만료
//                    val intent : Intent = if (response.data?.me?.id == null) {
//                        Intent(this@SplashActivity, LoginActivity::class.java)
//                    } else {
//                        Intent(this@SplashActivity, HomeActivity::class.java)
//                    }
//                    startActivity(intent)
                }
            } else {
                finish()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }, 1000)
    }
}