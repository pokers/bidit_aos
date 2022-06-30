package com.alexk.bidit.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

////반드시 알맞는 스코프를 사용
////Application, Activity, Fragment..
////Module 클래스에 반드시 InstallIn을 해주기
@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    @Singleton
    @Provides
    fun provideApolloService() = ApolloClient()
}