package com.alexk.bidit.data.remote.repository

import com.alexk.bidit.*
import com.alexk.bidit.GlobalApplication.Companion.userId
import com.alexk.bidit.common.util.ErrorItemNotFound
import com.alexk.bidit.di.ApolloClient
import com.alexk.bidit.domain.entity.item.ItemBasicEntity
import com.alexk.bidit.domain.entity.item.category.ItemCategoryRequestEntity
import com.alexk.bidit.domain.entity.item.connection.ItemConnectionEntity
import com.alexk.bidit.domain.entity.item.connection.ItemEdgeEntity
import com.alexk.bidit.domain.entity.item.connection.ItemPageInfoEntity
import com.alexk.bidit.domain.entity.item.img.ItemImgEntity
import com.alexk.bidit.domain.entity.user.UserBasicEntity
import com.alexk.bidit.domain.entity.user.socialLogin.UserKakaoLoginEntity
import com.alexk.bidit.domain.repository.ItemRepository
import com.alexk.bidit.type.CursorType
import com.alexk.bidit.type.ItemAddInput
import com.alexk.bidit.type.ItemQueryInput
import com.alexk.bidit.type.ItemUpdateInput
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import java.security.cert.PKIXRevocationChecker.Option
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(private val apiService: ApolloClient) :
    ItemRepository {
    override suspend fun retrieveItemInfo(id: Int): ItemBasicEntity {
        val itemInfo = ItemBasicEntity()

        fun typeCastItemImgList(getItemImageList: List<GetItemInfoQuery.Image?>?): List<ItemImgEntity> {
            val itemImgList = mutableListOf<ItemImgEntity>()
            getItemImageList?.forEach {
                itemImgList.add(ItemImgEntity(it?.url))
            }
            return itemImgList.toList()
        }

        fun typeCastItemUserInfo(info: GetItemInfoQuery.User): UserBasicEntity {
            val userInfo = UserBasicEntity()
            userInfo.apply {
                this.id = info.id
                this.nickname = info.nickname
                this.kakaoAccount = info.kakaoAccount?.let {
                    UserKakaoLoginEntity(
                        nickName = it.nickname,
                        profileImageUrl = it.profile_image_url
                    )
                }
            }
            return userInfo
        }

        try {
            val response = apiService.provideApolloClient().query(GetItemInfoQuery(getItemId = id))
                .execute().data?.getItem

            itemInfo.apply {
                this.id = response?.id
                this.status = response?.status
                this.sPrice = response?.sPrice
                this.cPrice = response?.cPrice
                this.buyNow = response?.buyNow
                this.viewCount = response?.viewCount
                this.title = response?.title
                this.createdAt = response?.createdAt
                this.dueDate = response?.dueDate
                this.description = response?.description?.description
                this.itemUserInfo = typeCastItemUserInfo(response?.user!!)
                this.itemImgList = typeCastItemImgList(response.image)
            }
        } catch (e: ApolloException) {
            throw ApolloException(ErrorItemNotFound)
        }
        return itemInfo
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
                        itemInfo?.buyNow,
                        itemInfo?.viewCount,
                        itemInfo?.title,
                        itemInfo?.createdAt,
                        itemInfo?.dueDate,
                        null,
                        null,
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
                        itemInfo?.buyNow,
                        itemInfo?.viewCount,
                        itemInfo?.title,
                        itemInfo?.createdAt,
                        itemInfo?.dueDate,
                        null,
                        null,
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
                        itemInfo?.buyNow,
                        itemInfo?.viewCount,
                        itemInfo?.title,
                        itemInfo?.createdAt,
                        itemInfo?.dueDate,
                        null,
                        null,
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
                        itemInfo?.buyNow,
                        itemInfo?.viewCount,
                        itemInfo?.title,
                        itemInfo?.createdAt,
                        itemInfo?.dueDate,
                        null,
                        null,
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
    ): ItemBasicEntity {
        val itemBasicEntity = ItemBasicEntity()
        try {
            val response = apiService.provideApolloClient().mutation(
                AddItemInfoMutation(
                    itemAdd = Optional.Present(inputItem),
                    description = Optional.Present(description),
                    images = Optional.Present(images)
                )
            ).execute().data?.addItem

            itemBasicEntity.id = response?.id
        } catch (e: ApolloException) {
            e.printStackTrace()
        }

        return itemBasicEntity
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

    override suspend fun retrieveItemCategoryFilterList(itemCategoryRequest: ItemCategoryRequestEntity): ItemConnectionEntity {
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
                            categoryId = Optional.Present(itemCategoryRequest.categoryId),
                            deliveryType = Optional.Present(itemCategoryRequest.deliveryType),
                            sCondition = Optional.Present(itemCategoryRequest.minUsingTime),
                            aCondition = Optional.Present(itemCategoryRequest.maxUsingTime)
                        )
                    ),
                    cursorTypeInfo = Optional.Present(itemCategoryRequest.cursorType)
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
                        itemInfo?.buyNow,
                        itemInfo?.viewCount,
                        itemInfo?.title,
                        itemInfo?.createdAt,
                        itemInfo?.dueDate,
                        null,
                        null,
                        imgList
                    )
                )
            }
            itemConnectionInfo.itemPageInfo = itemPageInfo
            itemConnectionInfo.totalCount = totalItemCount
            itemConnectionInfo.itemList =
                itemList.filter {
                    (it.status == 0 || it.status == 1)
                            && (it.sPrice!! >= itemCategoryRequest.minStartPrice && it.sPrice!! <= itemCategoryRequest.maxStartPrice)
                            && (it.buyNow!! >= itemCategoryRequest.minImmediatePrice && it.buyNow!! <= itemCategoryRequest.maxImmediatePrice)
                }

        } catch (e: ApolloException) {
            throw ApolloException(ErrorItemNotFound)
        }
        return itemConnectionInfo
    }
}
