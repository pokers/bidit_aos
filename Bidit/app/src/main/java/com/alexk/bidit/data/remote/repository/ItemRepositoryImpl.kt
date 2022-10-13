package com.alexk.bidit.data.remote.repository

import com.alexk.bidit.*
import com.alexk.bidit.common.util.ErrorItemNotFound
import com.alexk.bidit.di.ApolloClient
import com.alexk.bidit.domain.entity.item.ItemBasicEntity
import com.alexk.bidit.domain.entity.item.connection.ItemConnectionEntity
import com.alexk.bidit.domain.entity.item.connection.ItemEdgeEntity
import com.alexk.bidit.domain.entity.item.connection.ItemPageInfoEntity
import com.alexk.bidit.domain.entity.item.img.ItemImgEntity
import com.alexk.bidit.domain.repository.ItemRepository
import com.alexk.bidit.type.CursorType
import com.alexk.bidit.type.ItemAddInput
import com.alexk.bidit.type.ItemQueryInput
import com.alexk.bidit.type.ItemUpdateInput
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(private val apiService: ApolloClient) :
    ItemRepository {
    override suspend fun retrieveItemInfo(id: Int): ApolloResponse<GetItemInfoQuery.Data> {
        return apiService.provideApolloClient().query(GetItemInfoQuery(getItemId = id)).execute()
    }

    override suspend fun retrieveCursorTypeItemList(
        firstInfo: Int,
        lastInfo: Int,
        cursorType: CursorType
    ): ItemConnectionEntity {

        val itemConnectionInfo = ItemConnectionEntity()

        fun typeCastItemImgList(getItemImageList: List<GetItemListQuery.Image?>?): List<ItemImgEntity> {
            val itemImgList = mutableListOf<ItemImgEntity>()
            getItemImageList?.forEach {
                itemImgList.add(ItemImgEntity(it?.url))
            }
            return itemImgList.toList()
        }

        try {
            val response = apiService.provideApolloClient()
                .query(
                    GetItemListQuery(
                        firstInfo = Optional.Present(firstInfo),
                        lastInfo = Optional.Present(lastInfo),
                        cursorTypeInfo = Optional.Present(cursorType)
                    )
                ).execute().data?.getItemList

            val itemPageInfo = response?.pageInfo?.let {
                ItemPageInfoEntity(it.startCursor, it.endCursor, it.hasNextPage, it.hasPrevPage)
            }
            val totalItemCount = response?.totalCount
            val itemList = mutableListOf<ItemBasicEntity>()

            response?.edges?.forEach {
                val itemInfo = it?.node
                val imgList = typeCastItemImgList(itemInfo?.image)

                itemList.add(
                    ItemBasicEntity(
                        itemInfo?.id,
                        itemInfo?.status,
                        itemInfo?.sPrice,
                        itemInfo?.cPrice,
                        itemInfo?.viewCount,
                        itemInfo?.title,
                        itemInfo?.createdAt,
                        itemInfo?.dueDate,
                        imgList
                    )

                )
            }
            itemConnectionInfo.itemPageInfo = itemPageInfo
            itemConnectionInfo.totalCount = totalItemCount
            itemConnectionInfo.itemList =
                itemList.filter { it.status == 0 || it.status == 1 }
        } catch (e: ApolloException) {
            throw ApolloException(ErrorItemNotFound)
        }
        return itemConnectionInfo
    }

    override suspend fun retrieveCategoryItemList(
        categoryId: Int,
        cursorType: CursorType
    ): ItemConnectionEntity {
        val itemConnectionInfo = ItemConnectionEntity()

        fun typeCastItemImgList(getItemImageList: List<GetItemListQuery.Image?>?): List<ItemImgEntity> {
            val itemImgList = mutableListOf<ItemImgEntity>()
            getItemImageList?.forEach {
                itemImgList.add(ItemImgEntity(it?.url))
            }
            return itemImgList.toList()
        }

        try {
            val response = apiService.provideApolloClient().query(
                GetItemListQuery(
                    itemQueryInfo = Optional.Present(
                        ItemQueryInput(
                            categoryId = Optional.Present(categoryId)
                        )
                    ), cursorTypeInfo = Optional.Present(cursorType)
                )
            ).execute().data?.getItemList

            val itemPageInfo = response?.pageInfo?.let {
                ItemPageInfoEntity(it.startCursor, it.endCursor, it.hasNextPage, it.hasPrevPage)
            }
            val totalItemCount = response?.totalCount
            val itemList = mutableListOf<ItemBasicEntity>()

            response?.edges?.forEach {
                val itemInfo = it?.node
                val imgList = typeCastItemImgList(itemInfo?.image)
                itemList.add(
                    ItemBasicEntity(
                        itemInfo?.id,
                        itemInfo?.status,
                        itemInfo?.sPrice,
                        itemInfo?.cPrice,
                        itemInfo?.viewCount,
                        itemInfo?.title,
                        itemInfo?.createdAt,
                        itemInfo?.dueDate,
                        imgList
                    )
                )
            }
            itemConnectionInfo.itemPageInfo = itemPageInfo
            itemConnectionInfo.totalCount = totalItemCount
            itemConnectionInfo.itemList =
                itemList.filter { it.status == 0 || it.status == 1 }
        } catch (e: ApolloException) {
            throw ApolloException(ErrorItemNotFound)
        }
        return itemConnectionInfo
    }

    override suspend fun retrieveKeywordItemList(
        keyword: String,
        cursorType: CursorType
    ): ItemConnectionEntity {
        val itemConnectionInfo = ItemConnectionEntity()

        fun typeCastItemImgList(getItemImageList: List<GetItemListQuery.Image?>?): List<ItemImgEntity> {
            val itemImgList = mutableListOf<ItemImgEntity>()
            getItemImageList?.forEach {
                itemImgList.add(ItemImgEntity(it?.url))
            }
            return itemImgList.toList()
        }

        try {
            val response = apiService.provideApolloClient().query(
                GetItemListQuery(
                    keywordInfo = Optional.Present(keyword),
                    cursorTypeInfo = Optional.presentIfNotNull(cursorType)
                )
            ).execute().data?.getItemList

            val itemPageInfo = response?.pageInfo?.let {
                ItemPageInfoEntity(it.startCursor, it.endCursor, it.hasNextPage, it.hasPrevPage)
            }
            val totalItemCount = response?.totalCount
            val itemList = mutableListOf<ItemBasicEntity>()

            response?.edges?.forEach {
                val itemInfo = it?.node
                val imgList = typeCastItemImgList(itemInfo?.image)
                itemList.add(
                    ItemBasicEntity(
                        itemInfo?.id,
                        itemInfo?.status,
                        itemInfo?.sPrice,
                        itemInfo?.cPrice,
                        itemInfo?.viewCount,
                        itemInfo?.title,
                        itemInfo?.createdAt,
                        itemInfo?.dueDate,
                        imgList
                    )
                )
            }
            itemConnectionInfo.itemPageInfo = itemPageInfo
            itemConnectionInfo.totalCount = totalItemCount
            itemConnectionInfo.itemList =
                itemList.filter { it.status == 0 || it.status == 1 }
        } catch (e: ApolloException) {
            throw ApolloException(ErrorItemNotFound)
        }
        return itemConnectionInfo
    }

    override suspend fun retrieveMyItemList(userId: Int): ItemConnectionEntity {
        val itemConnectionInfo = ItemConnectionEntity()

        fun typeCastItemImgList(getItemImageList: List<GetItemListQuery.Image?>?): List<ItemImgEntity> {
            val itemImgList = mutableListOf<ItemImgEntity>()
            getItemImageList?.forEach {
                itemImgList.add(ItemImgEntity(it?.url))
            }
            return itemImgList.toList()
        }

        try {
            val response = apiService.provideApolloClient().query(
                GetItemListQuery(
                    itemQueryInfo = Optional.Present(
                        ItemQueryInput(userId = Optional.Present(userId))
                    ),
                    cursorTypeInfo = Optional.Present(CursorType.dueDate)
                )
            ).execute().data?.getItemList

            val itemPageInfo = response?.pageInfo?.let {
                ItemPageInfoEntity(it.startCursor, it.endCursor, it.hasNextPage, it.hasPrevPage)
            }
            val totalItemCount = response?.totalCount
            val itemList = mutableListOf<ItemBasicEntity>()

            response?.edges?.forEach {
                val itemInfo = it?.node
                val imgList = typeCastItemImgList(itemInfo?.image)
                itemList.add(
                        ItemBasicEntity(
                            itemInfo?.id,
                            itemInfo?.status,
                            itemInfo?.sPrice,
                            itemInfo?.cPrice,
                            itemInfo?.viewCount,
                            itemInfo?.title,
                            itemInfo?.createdAt,
                            itemInfo?.dueDate,
                            imgList
                    )
                )
            }
            itemConnectionInfo.itemPageInfo = itemPageInfo
            itemConnectionInfo.totalCount = totalItemCount
            itemConnectionInfo.itemList =
                itemList.filter { it.status == 0 || it.status == 1 }

        } catch (e: ApolloException) {
            throw ApolloException(ErrorItemNotFound)
        }
        return itemConnectionInfo
    }

    override suspend fun updateItemStatus(
        itemId: Int,
        status: Int
    ): ApolloResponse<UpdateItemMutation.Data> {
        return apiService.provideApolloClient().mutation(
            UpdateItemMutation(
                itemId = Optional.Present(itemId), itemUpdate = Optional.Present(
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

    override suspend fun updateItem(
        itemId: Int,
        itemInfo: ItemUpdateInput,
        description: String
    ): ApolloResponse<UpdateItemMutation.Data> {
        return apiService.provideApolloClient().mutation(
            UpdateItemMutation(
                itemId = Optional.Present(itemId),
                itemUpdate = Optional.Present(
                    ItemUpdateInput()
                ),
                descrption = Optional.Present(description)
            )
        ).execute();
    }
}
