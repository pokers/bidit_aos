package com.alexk.bidit.di

import android.app.Application
import com.alexk.bidit.GlobalApplication
import com.alexk.bidit.data.interceptor.TokenInterceptor
import com.alexk.bidit.data.sharedPreference.TokenManager
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import okhttp3.OkHttpClient
import javax.inject.Singleton

object NetworkModule {

    val token = TokenManager(GlobalApplication.applicationContext()).getToken()

    fun provideApolloClient(): ApolloClient {
        val logging = HttpLoggingClient.provideHttpLoggingClient()

        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(TokenInterceptor())

        return ApolloClient.Builder()
            .serverUrl(GlobalApplication.baseUrl)
            .okHttpClient(okHttpClient.build())
            .build()
    }

}