package com.alexk.bidit.data.repository


import android.util.Log
import com.alexk.bidit.GetMyInfoQuery
import com.alexk.bidit.UpdatePushTokenMutation
import com.alexk.bidit.di.ApolloClient
import com.alexk.bidit.domain.repository.UserRepository
import com.apollographql.apollo3.api.ApolloResponse
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService : ApolloClient) : UserRepository {
    override suspend fun checkToken(): ApolloResponse<GetMyInfoQuery.Data> {
        return apiService.provideApolloClient().query(GetMyInfoQuery()).execute()
    }

    override suspend fun updatePushToken(): ApolloResponse<UpdatePushTokenMutation.Data> {
        return apiService.provideApolloClient().mutation(UpdatePushTokenMutation()).execute()
    }
}