package com.alexk.bidit.data.remote.repository

import android.util.Log
import com.alexk.bidit.*
import com.alexk.bidit.common.util.ErrorCouldNotAdd
import com.alexk.bidit.common.util.ErrorInvalidToken
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
import com.apollographql.apollo3.exception.ApolloException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApolloClient) :
    UserRepository {
    override suspend fun getMyInfo(): UserBasicEntity {
        val result = UserBasicEntity()
        try {
            val getMyInfoData =
                apiService.provideApolloClient().query(GetMyInfoQuery()).execute().data?.me

            val userPushToken = getMyInfoData?.pushToken?.let {
                UserPushTokenEntity(
                    id = it.id,
                    status = it.status,
                    userId = it.userId,
                    token = it.token
                )
            }

            val userAlarm = getMyInfoData?.userAlarm?.let {
                UserAlarmEntity(id = it.id, status = it.status, alarmId = it.alarmId)
            }

            val userSocialLoginEntity = getMyInfoData?.kakaoAccount?.let {
                UserKakaoLoginEntity(
                    name = it.name,
                    email = it.email,
                    phoneNumber = it.phone_number,
                    nickName = it.nickname,
                    profileImageUrl = it.profile_image_url
                )
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

        } catch (e: ApolloException) {
            e.printStackTrace()
        }

        return result
    }

    override suspend fun addUserInfo(): Int {
        val userId: Int?
        try {
            val response = apiService.provideApolloClient().mutation(PostMyInfoMutation())
                .execute().data?.addUser

            userId = response?.id

        } catch (e: ApolloException) {
            throw ApolloException(ErrorCouldNotAdd)
        }
        return userId!!
    }

    override suspend fun updatePushToken(
        status: Int?,
        pushToken: String
    ): Boolean {
        var result = false
        try {
            val response = apiService.provideApolloClient().mutation(
                UpdatePushTokenMutation(
                    Optional.Present(
                        PushTokenUpdateInput(Optional.Present(status), Optional.Present(pushToken))
                    )
                )
            ).execute().data

            result = response?.updatePushToken!!
        } catch (e: ApolloException) {
            e.printStackTrace()
        }
        return result
    }

    override suspend fun updateUserStatus(status: MembershipStatus): UserBasicEntity {
        val userBasicEntity = UserBasicEntity()

        try {
            val response = apiService.provideApolloClient()
                .mutation(UpdateUserStatusMutation(Optional.Present(status)))
                .execute().data?.updateMembership

            userBasicEntity.apply {
                this.id = response?.id
            }
        } catch (e: ApolloException) {
            throw ApolloException(ErrorUserNotFound)
        }

        return userBasicEntity
    }

    override suspend fun updateUserInfo(
        nickname: String,
        profileImg: String?
    ): UserBasicEntity {
        val userBasicEntity = UserBasicEntity()

        try {
            val response = apiService.provideApolloClient().mutation(
                UpdateUserInfoMutation(
                    Optional.Present(
                        UserUpdateInput(
                            nickname = Optional.Present(nickname)
                        )
                    )
                )
            ).execute().data?.updateUser

            userBasicEntity.apply {
                this.id = response?.id
                this.nickname = response?.nickname
            }

        } catch (e: ApolloException) {
            throw ApolloException(ErrorUserNotFound)
        }
        return userBasicEntity
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