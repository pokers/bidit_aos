package com.alexk.bidit.data.repository


import com.alexk.bidit.GetMyInfoQuery
import com.alexk.bidit.di.ApolloClient
import com.alexk.bidit.domain.repository.LoginRepository
import com.apollographql.apollo.api.Response
import com.apollographql.apollo3.api.ApolloResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

//Inject 는 의존성 주입을 하겠다는 말
//Singleton 스코프를 추가하면 1개의 인스턴스만 사용.
//impl -> 만든 레포지토리를 구현한다.

class LoginRepositoryImpl @Inject constructor(private val apiService : ApolloClient) : LoginRepository {
    override suspend fun checkToken(): ApolloResponse<GetMyInfoQuery.Data> {
        return apiService.provideApolloClient().query(GetMyInfoQuery()).execute()
    }
}