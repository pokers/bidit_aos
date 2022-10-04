package com.alexk.bidit.domain.repository

import com.alexk.bidit.*
import com.alexk.bidit.domain.entity.user.UserBasicEntity
import com.alexk.bidit.type.MembershipStatus
import com.apollographql.apollo3.api.ApolloResponse

//인터페이스로 사용할 레포를 선언해준다.
interface UserRepository {
    suspend fun getMyInfo() : UserBasicEntity
    suspend fun addUserInfo() : ApolloResponse<PostMyInfoMutation.Data>
    suspend fun updatePushToken(status : Int?, pushToken:String): ApolloResponse<UpdatePushTokenMutation.Data>
    suspend fun updateUserStatus(status: MembershipStatus):ApolloResponse<DeleteUserInfoMutation.Data>
    suspend fun updateUserInfo(nickname : String, profileImg:String?) : ApolloResponse<UpdateUserInfoMutation.Data>
    suspend fun addAlarm(userId : Int, status: Int):ApolloResponse<SetUserAlarmMutation.Data>
}