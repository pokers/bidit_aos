package com.alexk.bidit.presentation.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexk.bidit.*
import com.alexk.bidit.domain.repository.ItemRepository
import com.apollographql.apollo3.api.ApolloResponse
import com.alexk.bidit.common.util.value.ViewState
import com.alexk.bidit.domain.entity.item.ItemBasicEntity
import com.alexk.bidit.domain.entity.item.category.ItemCategoryRequestEntity
import com.alexk.bidit.domain.entity.item.connection.ItemConnectionEntity
import com.alexk.bidit.type.CursorType
import com.alexk.bidit.type.ItemAddInput
import com.apollographql.apollo3.exception.ApolloHttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ItemViewModel @Inject constructor(private val repository: ItemRepository) :
    ViewModel() {

    private val _itemList by lazy { MutableLiveData<ViewState<ItemConnectionEntity>>() }
    val itemList get() = _itemList

    private val _itemInfo by lazy { MutableLiveData<ViewState<ItemBasicEntity>>() }
    val itemInfo get() = _itemInfo

    private val _updateItem by lazy { MutableLiveData<ViewState<ApolloResponse<UpdateItemMutation.Data>>>() }
    val updateItem get() = _updateItem

    private val _addItemStatus by lazy { MutableLiveData<ViewState<ApolloResponse<AddItemInfoMutation.Data>>>() }
    val addItemStatus get() = _addItemStatus

    fun getItemInfo(id: Int) = viewModelScope.launch {
        _itemInfo.postValue(ViewState.Loading())
        try {
            val response = repository.retrieveItemInfo(id = id)
            _itemInfo.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e("MY_ITEM_LIST", "Error GET item list")
            this@ItemViewModel._itemInfo.postValue(ViewState.Error("Error fetching ItemInfo"))
        }
    }

    fun addItemInfo(inputItem: ItemAddInput, description: String, images: List<String>) =
        viewModelScope.launch {
            _addItemStatus.postValue(ViewState.Loading())
            try {
                val response = repository.addItemInfo(inputItem, description, images)
                _addItemStatus.postValue(ViewState.Success(response))
            } catch (e: ApolloHttpException) {
                _addItemStatus.postValue(ViewState.Error("Error add item"))
            }
        }

    fun getSortTypeItemList(firstInfo: Int, lastInfo: Int, cursorType: CursorType) =
        viewModelScope.launch {
            this@ItemViewModel._itemList.postValue(ViewState.Loading())
            try {
                val response = repository.retrieveCursorTypeItemList(
                    firstInfo = firstInfo,
                    lastInfo = lastInfo,
                    cursorType = cursorType
                )
                this@ItemViewModel._itemList.postValue(ViewState.Success(response))

            } catch (e: ApolloHttpException) {
                Log.e("ApolloException", "Failure", e)
                this@ItemViewModel._itemList.postValue(ViewState.Error("Error fetching ItemList"))
            }
        }

    fun getCategoryItemList(categoryId: Int, cursorType: CursorType) = viewModelScope.launch {
        this@ItemViewModel._itemList.postValue(ViewState.Loading())
        try {
            val response = repository.retrieveCategoryItemList(categoryId, cursorType)
            this@ItemViewModel._itemList.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e("ApolloException", "Failure", e)
            this@ItemViewModel._itemList.postValue(ViewState.Error("Error fetching latestOrderItemList"))
        }
    }

    fun getKeywordItemList(keyword: String, cursorType: CursorType) = viewModelScope.launch {
        this@ItemViewModel._itemList.postValue(ViewState.Loading())
        try {
            val response = repository.retrieveKeywordItemList(keyword, cursorType)
            this@ItemViewModel._itemList.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e("ApolloException", "Failure", e)
            this@ItemViewModel._itemList.postValue(ViewState.Error("Error fetching latestOrderItemList"))
        }
    }

    fun updateItemStatus(itemId: Int, status: Int) = viewModelScope.launch {
        _updateItem.postValue(ViewState.Loading())
        try {
            val response = repository.updateItemStatus(itemId, status)
            _updateItem.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e("ApolloException", "Failure", e)
            _updateItem.postValue(ViewState.Error("Error update item status"))
        }
    }

    fun getCategoryFilterItemList(itemCategoryRequest: ItemCategoryRequestEntity) = viewModelScope.launch {
        _itemList.postValue(ViewState.Loading())
        try{
            val response = repository.retrieveItemCategoryFilterList(itemCategoryRequest)
            _itemList.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            _updateItem.postValue(ViewState.Error("Error update item status"))
        }
    }
}
