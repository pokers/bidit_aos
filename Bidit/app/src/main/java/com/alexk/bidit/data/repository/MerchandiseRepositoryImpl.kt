package com.alexk.bidit.data.repository

import com.alexk.bidit.GetItemInfoQuery
import com.alexk.bidit.GetMyInfoQuery
import com.alexk.bidit.di.ApolloClient
import com.alexk.bidit.domain.repository.LoginRepository
import com.alexk.bidit.domain.repository.MerchandiseRepository
import com.apollographql.apollo3.api.ApolloResponse
import javax.inject.Inject

class MerchandiseRepositoryImpl @Inject constructor(private val apiService: ApolloClient) :
    MerchandiseRepository {
    override suspend fun getItemList(id: Int): ApolloResponse<GetItemInfoQuery.Data> {
        return apiService.provideApolloClient().query(GetItemInfoQuery(id)).execute()
    }
}
