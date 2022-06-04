package com.alexk.bidit.data.repository

import com.alexk.bidit.data.service.response.home.HomeResponse
import javax.inject.Inject
import javax.inject.Singleton

//Inject 는 의존성 주입을 하겠다는 말
//Singleton 스코프를 추가하면 1개의 인스턴스만 사용.
@Singleton
class SignRepositoryImpl @Inject constructor(
    private val HomeResponse: HomeResponse
) {

    fun load(id: String) {

    }
}