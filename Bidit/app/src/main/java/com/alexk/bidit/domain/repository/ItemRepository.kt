package com.alexk.bidit.domain.repository

import com.alexk.bidit.*
import com.alexk.bidit.domain.entity.item.ItemBasicEntity
import com.alexk.bidit.domain.entity.item.category.ItemCategoryRequestEntity
import com.alexk.bidit.domain.entity.item.connection.ItemConnectionEntity
import com.alexk.bidit.type.CursorType
import com.alexk.bidit.type.ItemAddInput
import com.alexk.bidit.type.ItemUpdateInput
import com.apollographql.apollo3.api.ApolloResponse

interface ItemRepository {
    suspend fun retrieveItemInfo(id: Int): ItemBasicEntity
    suspend fun retrieveCursorTypeItemList(firstInfo : Int, lastInfo : Int, cursorType: CursorType): ItemConnectionEntity
    suspend fun retrieveCategoryItemList(
        categoryId: Int,
        cursorType: CursorType
    ): ItemConnectionEntity

    suspend fun retrieveKeywordItemList(
        keyword: String,
        cursorType: CursorType
    ): ItemConnectionEntity

    suspend fun retrieveMyItemList(userId: Int): ItemConnectionEntity

    suspend fun addItemInfo(
        inputItem: ItemAddInput,
        description: String,
        images: List<String>
    ): ItemBasicEntity

    suspend fun updateItemStatus(
        itemId: Int,
        status: Int
    ): ApolloResponse<UpdateItemMutation.Data>

    suspend fun updateItem(
        itemId: Int,
        itemInfo: ItemUpdateInput,
        description: String
    ): ApolloResponse<UpdateItemMutation.Data>

    suspend fun retrieveItemCategoryFilterList(itemCategoryRequest: ItemCategoryRequestEntity):ItemConnectionEntity
}
