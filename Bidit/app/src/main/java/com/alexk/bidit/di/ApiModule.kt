package com.alexk.bidit.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext

//반드시 알맞는 스코프를 사용
//Application, Activity, Fragment..
//Module 클래스에 반드시 InstallIn을 해주기
@InstallIn(ActivityComponent::class)
@Module
object ApiModule {
    @Provides
    //Context를 어디서 사용하는지 반드시 명시
    //Application, Activity, Fragment..
    fun provideTest(@ActivityContext context: Context){

    }
}