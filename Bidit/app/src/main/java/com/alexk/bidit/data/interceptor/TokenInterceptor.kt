package com.alexk.bidit.data.interceptor

import com.alexk.bidit.GlobalApplication
import com.alexk.bidit.data.sharedPreference.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization","Bearer " + TokenManager(GlobalApplication.applicationContext()).getToken() + " kakao")
            .build()

        return chain.proceed(request)
    }
}