package com.alexk.bidit.di

import com.alexk.bidit.data.remote.repository.BiddingRepositoryImpl
import com.alexk.bidit.data.remote.repository.ItemImgRepositoryImpl
import com.alexk.bidit.data.remote.repository.UserRepositoryImpl
import com.alexk.bidit.data.remote.repository.ItemRepositoryImpl
import com.alexk.bidit.domain.repository.BiddingRepository
import com.alexk.bidit.domain.repository.ItemImgRepository
import com.alexk.bidit.domain.repository.UserRepository
import com.alexk.bidit.domain.repository.ItemRepository
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
    abstract fun bindLoginRepository(repo: UserRepositoryImpl): UserRepository

    @Binds
    @ViewModelScoped
    abstract fun bindMerchandiseRepository(repo: ItemRepositoryImpl): ItemRepository

    @Binds
    @ViewModelScoped
    abstract fun bindBiddingRepository(repo: BiddingRepositoryImpl): BiddingRepository

    @Binds
    @ViewModelScoped
    abstract fun bindItemImgRepository(repo: ItemImgRepositoryImpl): ItemImgRepository
}