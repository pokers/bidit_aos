package com.alexk.bidit.data.repository

import com.alexk.bidit.*
import com.alexk.bidit.di.ApolloClient
import com.alexk.bidit.domain.repository.MerchandiseRepository
import com.alexk.bidit.type.CursorType
import com.alexk.bidit.type.ItemQueryInput
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import javax.inject.Inject

class MerchandiseRepositoryImpl @Inject constructor(private val apiService: ApolloClient) :
    MerchandiseRepository {
    override suspend fun getItem(id: Int): ApolloResponse<GetItemInfoQuery.Data> {
        TODO("Not yet implemented")
    }

    override suspend fun getCursorTypeItemList(cursorType: CursorType): ApolloResponse<GetItemListQuery.Data> {
        return apiService.provideApolloClient()
            .query(GetItemListQuery(cursorTypeInfo = Optional.Present(cursorType))).execute()
    }

    override suspend fun getCategoryItemList(
        categoryId: Int,
        cursorType: CursorType
    ): ApolloResponse<GetItemListQuery.Data> {
        return apiService.provideApolloClient().query(
            GetItemListQuery(
                itemQueryInfo = Optional.Present(
                    ItemQueryInput(
                        categoryId = Optional.Present(categoryId)
                    )
                ), cursorTypeInfo = Optional.Present(cursorType)
            )
        ).execute()
    }
}
