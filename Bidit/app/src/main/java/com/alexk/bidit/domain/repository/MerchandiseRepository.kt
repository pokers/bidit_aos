package com.alexk.bidit.domain.repository

import com.alexk.bidit.*
import com.alexk.bidit.type.CursorType
import com.apollographql.apollo3.api.ApolloResponse

interface MerchandiseRepository {
    suspend fun retrieveItemInfo(id : Int) : ApolloResponse<GetItemInfoQuery.Data>

    suspend fun retrieveCursorTypeItemList(cursorType: CursorType):ApolloResponse<GetItemListQuery.Data>

    suspend fun retrieveCategoryItemList(categoryId : Int, cursorType: CursorType):ApolloResponse<GetItemListQuery.Data>

    suspend fun retrieveKeywordItemList(keyword : String, cursorType: CursorType) : ApolloResponse<GetItemListQuery.Data>
}
