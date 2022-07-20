package com.alexk.bidit.data.repository


import android.util.Log
import com.alexk.bidit.DeleteUserInfoMutation
import com.alexk.bidit.GetMyInfoQuery
import com.alexk.bidit.UpdatePushTokenMutation
import com.alexk.bidit.di.ApolloClient
import com.alexk.bidit.domain.repository.UserRepository
import com.alexk.bidit.type.MembershipStatus
import com.alexk.bidit.type.PushTokenUpdateInput
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApolloClient) :
    UserRepository {
    override suspend fun getMyInfo(): ApolloResponse<GetMyInfoQuery.Data> {
        return apiService.provideApolloClient().query(GetMyInfoQuery()).execute()
    }

    override suspend fun updatePushToken(pushToken: String): ApolloResponse<UpdatePushTokenMutation.Data> {
        return apiService.provideApolloClient().mutation(
            UpdatePushTokenMutation(
                Optional.Present(
                    PushTokenUpdateInput(Optional.Present(null), Optional.Present(pushToken))
                )
            )
        ).execute()
    }

    override suspend fun updateUserStatus(status: MembershipStatus): ApolloResponse<DeleteUserInfoMutation.Data> {
        return apiService.provideApolloClient()
            .mutation(DeleteUserInfoMutation(Optional.Present(status))).execute()
    }
}