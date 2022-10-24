package com.alexk.bidit.common.util.interceptor

import com.alexk.bidit.common.util.sharePreference.UserTokenManager
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization","Bearer " + UserTokenManager.getKakaoAccessToken() + " kakao")
            .build()

        return chain.proceed(request)
    }
}