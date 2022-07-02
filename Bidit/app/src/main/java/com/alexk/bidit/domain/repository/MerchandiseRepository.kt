package com.alexk.bidit.domain.repository

import com.alexk.bidit.*
import com.alexk.bidit.type.CursorType
import com.apollographql.apollo3.api.ApolloResponse

interface MerchandiseRepository {
    suspend fun getItem(id : Int) : ApolloResponse<GetItemInfoQuery.Data>

    suspend fun getCursorTypeItemList(cursorType: CursorType):ApolloResponse<GetItemListQuery.Data>

    suspend fun getCategoryItemList(categoryId : Int, cursorType: CursorType):ApolloResponse<GetItemListQuery.Data>
}