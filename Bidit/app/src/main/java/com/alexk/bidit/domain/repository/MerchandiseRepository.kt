package com.alexk.bidit.domain.repository

import com.alexk.bidit.GetItemInfoQuery
import com.alexk.bidit.GetMyInfoQuery
import com.apollographql.apollo3.api.ApolloResponse

interface MerchandiseRepository {
    suspend fun getItemList(id : Int) : ApolloResponse<GetItemInfoQuery.Data>
}