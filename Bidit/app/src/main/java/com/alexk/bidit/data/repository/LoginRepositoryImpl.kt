package com.alexk.bidit.data.repository


import com.alexk.bidit.GetMyInfoQuery
import com.alexk.bidit.di.ApolloClient
import com.alexk.bidit.domain.repository.LoginRepository
import com.apollographql.apollo.api.Response
import com.apollographql.apollo3.api.ApolloResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val apiService : ApolloClient) : LoginRepository {
    override suspend fun checkToken(): ApolloResponse<GetMyInfoQuery.Data> {
        return apiService.provideApolloClient().query(GetMyInfoQuery()).execute()
    }
}