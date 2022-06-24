package com.alexk.bidit.di

import okhttp3.logging.HttpLoggingInterceptor

object HttpLoggingClient {
    fun provideHttpLoggingClient():HttpLoggingInterceptor{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }
}