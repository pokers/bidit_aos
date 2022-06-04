package com.alexk.bidit.data.interceptor

import com.alexk.bidit.GlobalApplication
import com.alexk.bidit.data.sharedPreference.TokenManager
import okhttp3.OkHttpClient

class TokenInterceptor {
    val httpClientBuilder = OkHttpClient().newBuilder().apply {
        this.addInterceptor {
            val token = TokenManager(GlobalApplication.applicationContext()).getToken()
            val builder = it.request().newBuilder()
            if (token.isNotEmpty()) {
                builder.addHeader("HEADER-NAME", token)
            }
            return@addInterceptor it.proceed(builder.build())
        }
    }
}