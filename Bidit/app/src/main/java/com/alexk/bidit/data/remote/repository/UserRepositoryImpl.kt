package com.alexk.bidit.data.remote.repository

import com.alexk.bidit.*
import com.alexk.bidit.common.util.ErrorUserNotFound
import com.alexk.bidit.di.ApolloClient
import com.alexk.bidit.domain.entity.user.*
import com.alexk.bidit.domain.entity.user.alarm.UserAlarmEntity
import com.alexk.bidit.domain.entity.user.pushToken.UserPushTokenEntity
import com.alexk.bidit.domain.entity.user.socialLogin.UserKakaoLoginEntity
import com.alexk.bidit.domain.repository.UserRepository
import com.alexk.bidit.type.MembershipStatus
import com.alexk.bidit.type.PushTokenUpdateInput
import com.alexk.bidit.type.UserUpdateInput
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApolloClient) :
    UserRepository {
    override suspend fun getMyInfo(): UserBasicEntity {
        val result = UserBasicEntity()
        try{
            val getMyInfoData = apiService.provideApolloClient().query(GetMyInfoQuery()).execute().data?.me

            val userPushToken = getMyInfoData?.pushToken?.let {
                UserPushTokenEntity(id = it.id, status = it.status, userId = it.userId, token = it.token)
            }

            val userAlarm = getMyInfoData?.userAlarm?.let {
                UserAlarmEntity(id = it.id, status = it.status, alarmId = it.alarmId)
            }

            val userSocialLoginEntity = getMyInfoData?.kakaoAccount?.let {
                UserKakaoLoginEntity(name = it.name, email = it.email, phoneNumber = it.phone_number, nickName = it.nickname, profileImageUrl = it.profile_image_url)
            }

            result.apply {
                id = getMyInfoData?.id
                nickname = getMyInfoData?.nickname
                joinPath = getMyInfoData?.joinPath
                email = getMyInfoData?.email
                pushToken = userPushToken
                alarm = userAlarm
                kakaoAccount = userSocialLoginEntity
            }

        }catch (e : Exception){
            throw NullPointerException(ErrorUserNotFound)
        }

        return result
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