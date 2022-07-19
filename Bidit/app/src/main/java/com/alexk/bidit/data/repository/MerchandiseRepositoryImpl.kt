package com.alexk.bidit.data.repository

import com.alexk.bidit.*
import com.alexk.bidit.di.ApolloClient
import com.alexk.bidit.domain.repository.MerchandiseRepository
import com.alexk.bidit.type.CursorType
import com.alexk.bidit.type.ItemAddInput
import com.alexk.bidit.type.ItemQueryInput
import com.alexk.bidit.type.ItemUpdateInput
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import javax.inject.Inject

class MerchandiseRepositoryImpl @Inject constructor(private val apiService: ApolloClient) :
    MerchandiseRepository {
    override suspend fun retrieveItemInfo(id: Int): ApolloResponse<GetItemInfoQuery.Data> {
        return apiService.provideApolloClient().query(GetItemInfoQuery(getItemId = id)).execute()
    }

    override suspend fun retrieveCursorTypeItemList(cursorType: CursorType): ApolloResponse<GetItemListQuery.Data> {
        return apiService.provideApolloClient()
            .query(GetItemListQuery(cursorTypeInfo = Optional.Present(cursorType))).execute()
    }

    override suspend fun retrieveCategoryItemList(
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

    override suspend fun retrieveKeywordItemList(
        keyword: String,
        cursorType: CursorType
    ): ApolloResponse<GetItemListQuery.Data> {
        return apiService.provideApolloClient().query(
            GetItemListQuery(
                keywordInfo = Optional.Present(keyword),
                cursorTypeInfo = Optional.presentIfNotNull(cursorType)
            )
        ).execute()
    }

    override suspend fun retrieveMyItemList(userId: Int): ApolloResponse<GetItemListQuery.Data> {
        return apiService.provideApolloClient().query(
            GetItemListQuery(
                itemQueryInfo = Optional.Present(
                    ItemQueryInput(userId = Optional.Present(userId))
                )
            )
        ).execute()
    }

    override suspend fun updateItemStatus(
        itemId: Int,
        status: Int
    ): ApolloResponse<UpdateItemStatusMutation.Data> {
        return apiService.provideApolloClient().mutation(
            UpdateItemStatusMutation(
                itemId = Optional.Present(itemId), itemStatus = Optional.Present(
                    ItemUpdateInput(status = Optional.Present(status))
                )
            )
        ).execute()
    }

    override suspend fun addItemInfo(
        inputItem: ItemAddInput,
        description: String,
        images: List<String>
    ): ApolloResponse<AddItemInfoMutation.Data> {
        return apiService.provideApolloClient().mutation(
            AddItemInfoMutation(
                itemAdd = Optional.Present(inputItem),
                description = Optional.Present(description),
                images = Optional.Present(images)
            )
        ).execute()
    }
}
