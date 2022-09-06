package com.alexk.bidit.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    @Singleton
    @Provides
    fun provideApolloService() = ApolloClient()

    @Singleton
    @Provides
    fun provideS3Service() = S3Client()
}
