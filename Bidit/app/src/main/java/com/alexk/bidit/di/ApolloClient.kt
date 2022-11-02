package com.alexk.bidit.di

import android.os.Looper
import com.alexk.bidit.common.util.interceptor.TokenInterceptor
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient

class ApolloClient() {

    fun provideApolloClient(): ApolloClient {
        check(Looper.myLooper() == Looper.getMainLooper()) {
            "Only the main thread can get the apolloClient instance"
        }
        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(TokenInterceptor())

        return ApolloClient.Builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient.build())
            .build()
    }

    companion object {
        private const val BASE_URL =
            "https://wypcpelqdbhlxgrexisgez7vba.appsync-api.ap-northeast-2.amazonaws.com/graphql/"
    }
}