package com.alexk.bidit.data.interceptor

import com.alexk.bidit.application.App
import com.alexk.bidit.data.sharedPreference.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class TokenInterceptor {
    val httpClientBuilder = OkHttpClient().newBuilder().apply {
        this.addInterceptor {
            val token = TokenManager(App.applicationContext()).getToken()
            val builder = it.request().newBuilder()
            if (token.isNotEmpty()) {
                builder.addHeader("HEADER-NAME", token)
            }
            return@addInterceptor it.proceed(builder.build())
        }
    }
}