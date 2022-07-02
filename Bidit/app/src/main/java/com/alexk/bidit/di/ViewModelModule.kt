package com.alexk.bidit.di

import com.alexk.bidit.data.repository.LoginRepositoryImpl
import com.alexk.bidit.data.repository.MerchandiseRepositoryImpl
import com.alexk.bidit.domain.repository.LoginRepository
import com.alexk.bidit.domain.repository.MerchandiseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {
    @Binds
    @ViewModelScoped
    abstract fun bindLoginRepository(repo: LoginRepositoryImpl): LoginRepository

    @Binds
    @ViewModelScoped
    abstract fun bindMerchandiseRepository(repo: MerchandiseRepositoryImpl): MerchandiseRepository
}