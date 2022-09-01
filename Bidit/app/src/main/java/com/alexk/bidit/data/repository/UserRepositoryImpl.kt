package com.alexk.bidit.data.repository


import android.util.Log
import com.alexk.bidit.*
import com.alexk.bidit.di.ApolloClient
import com.alexk.bidit.domain.repository.UserRepository
import com.alexk.bidit.type.MembershipStatus
import com.alexk.bidit.type.PushTokenUpdateInput
import com.alexk.bidit.type.UserUpdateInput
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApolloClient) :
    UserRepository {
    override suspend fun getMyInfo(): ApolloResponse<GetMyInfoQuery.Data> {
        return apiService.provideApolloClient().query(GetMyInfoQuery()).execute()
    }

    override suspend fun addUserInfo(): ApolloResponse<PostMyInfoMutation.Data> {
        return apiService.provideApolloClient().mutation(PostMyInfoMutation()).execute();
    }

    override suspend fun updatePushToken(
        status: Int?,
        pushToken: String
    ): ApolloResponse<UpdatePushTokenMutation.Data> {
        return apiService.provideApolloClient().mutation(
            UpdatePushTokenMutation(
                Optional.Present(
                    PushTokenUpdateInput(Optional.Present(status), Optional.Present(pushToken))
                )
            )
        ).execute()
    }

    override suspend fun updateUserStatus(status: MembershipStatus): ApolloResponse<DeleteUserInfoMutation.Data> {
        return apiService.provideApolloClient()
            .mutation(DeleteUserInfoMutation(Optional.Present(status))).execute()
    }

    override suspend fun updateUserInfo(
        nickname: String,
        profileImg: String?
    ): ApolloResponse<UpdateUserInfoMutation.Data> {
        return apiService.provideApolloClient().mutation(
            UpdateUserInfoMutation(
                Optional.Present(
                    UserUpdateInput(
                        nickname = Optional.Present(nickname)
                    )
                )
            )
        ).execute()
    }

    override suspend fun addAlarm(
        userId: Int,
        status: Int
    ): ApolloResponse<SetUserAlarmMutation.Data> {
        return apiService.provideApolloClient().mutation(
            SetUserAlarmMutation(
                userId = Optional.Present(userId),
                status = Optional.Present(status)
            )
        ).execute()
    }
}