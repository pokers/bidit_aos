package com.alexk.bidit.domain.repository

import com.alexk.bidit.GetMyInfoQuery
import com.alexk.bidit.UpdatePushTokenMutation
import com.alexk.bidit.type.User
import com.apollographql.apollo.api.Response
import com.apollographql.apollo3.api.ApolloResponse

//인터페이스로 사용할 레포를 선언해준다.
interface UserRepository {
    suspend fun checkToken() : ApolloResponse<GetMyInfoQuery.Data>
    suspend fun updatePushToken(): ApolloResponse<UpdatePushTokenMutation.Data>
}