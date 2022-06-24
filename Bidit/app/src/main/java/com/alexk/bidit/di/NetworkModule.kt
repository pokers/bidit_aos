package com.alexk.bidit.di

import android.app.Application
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideApolloClient():ApolloClient{
        val logging = HttpLoggingClient.provideHttpLoggingClient()

        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(logging)

        return ApolloClient.Builder()
            .serverUrl("123123")
            .okHttpClient(okHttpClient.build())
            .build()
    }

}