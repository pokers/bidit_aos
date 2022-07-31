package com.alexk.bidit.domain.repository

import com.alexk.bidit.*
import com.alexk.bidit.type.CursorType
import com.alexk.bidit.type.ItemAddInput
import com.alexk.bidit.type.ItemUpdateInput
import com.apollographql.apollo3.api.ApolloResponse

interface MerchandiseRepository {
    suspend fun retrieveItemInfo(id: Int): ApolloResponse<GetItemInfoQuery.Data>
    suspend fun retrieveCursorTypeItemList(cursorType: CursorType): ApolloResponse<GetItemListQuery.Data>
    suspend fun retrieveCategoryItemList(
        categoryId: Int,
        cursorType: CursorType
    ): ApolloResponse<GetItemListQuery.Data>

    suspend fun retrieveKeywordItemList(
        keyword: String,
        cursorType: CursorType
    ): ApolloResponse<GetItemListQuery.Data>

    suspend fun retrieveMyItemList(userId: Int): ApolloResponse<GetItemListQuery.Data>
    suspend fun addItemInfo(
        inputItem: ItemAddInput,
        description: String,
        images: List<String>
    ): ApolloResponse<AddItemInfoMutation.Data>

    suspend fun updateItemStatus(
        itemId: Int,
        status: Int
    ): ApolloResponse<UpdateItemMutation.Data>

    suspend fun updateItem(
        itemId: Int,
        itemInfo: ItemUpdateInput,
        description: String
    ): ApolloResponse<UpdateItemMutation.Data>
}
