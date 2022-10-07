package com.alexk.bidit.presentation.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexk.bidit.*
import com.alexk.bidit.domain.repository.ItemRepository
import com.apollographql.apollo3.api.ApolloResponse
import com.alexk.bidit.common.util.view.ViewState
import com.alexk.bidit.type.CursorType
import com.alexk.bidit.type.ItemAddInput
import com.alexk.bidit.type.ItemUpdateInput
import com.apollographql.apollo3.exception.ApolloHttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ItemViewModel @Inject constructor(private val repository: ItemRepository) :
    ViewModel() {

    private val _itemList by lazy { MutableLiveData<ViewState<ApolloResponse<GetItemListQuery.Data>>>() }
    val itemList get() = _itemList

    private val _itemInfo by lazy { MutableLiveData<ViewState<ApolloResponse<GetItemInfoQuery.Data>>>() }
    val itemInfo get() = _itemInfo

    private val _updateItem by lazy { MutableLiveData<ViewState<ApolloResponse<UpdateItemMutation.Data>>>() }
    val updateItem get() = _updateItem

    private val _addItemStatus by lazy { MutableLiveData<ViewState<ApolloResponse<AddItemInfoMutation.Data>>>() }
    val addItemStatus get() = _addItemStatus

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

    fun getMyItemList(id: Int) = viewModelScope.launch {
        this@ItemViewModel._itemList.postValue(ViewState.Loading())
        try {
            val response = repository.retrieveMyItemList(userId = id)
            this@ItemViewModel._itemList.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e("MY_ITEM_LIST", "Error GET my list")
            this@ItemViewModel._itemList.postValue(ViewState.Error("Error fetching ItemList"))
        }
    }

    fun getSortTypeItemList(firstInfo : Int, lastInfo : Int, sortType: String) = viewModelScope.launch {
        this@ItemViewModel._itemList.postValue(ViewState.Loading())
        try {
            when (sortType) {
                "latestOrder" -> {
                    val response = repository.retrieveCursorTypeItemList(firstInfo = firstInfo, lastInfo = lastInfo, cursorType = CursorType.createdAt)
                    this@ItemViewModel._itemList.postValue(ViewState.Success(response))
                }
                "deadline" -> {
                    val response = repository.retrieveCursorTypeItemList(firstInfo = firstInfo, lastInfo = lastInfo, cursorType = CursorType.dueDate)
                    this@ItemViewModel._itemList.postValue(ViewState.Success(response))
                }
            }
        } catch (e: ApolloHttpException) {
            Log.e("ApolloException", "Failure", e)
            this@ItemViewModel._itemList.postValue(ViewState.Error("Error fetching ItemList"))
        }
    }

    fun getCategoryItemList(categoryId: Int, sortType: String) = viewModelScope.launch {
        var cursorType: CursorType? = null

        when (sortType) {
            "latestOrder" -> {
                cursorType = CursorType.createdAt
            }
            "deadline" -> {
                cursorType = CursorType.dueDate
            }
            //후에 수정
            "popular" -> {
                cursorType = CursorType.dueDate
            }
        }
        this@ItemViewModel._itemList.postValue(ViewState.Loading())
        try {
            val response = repository.retrieveCategoryItemList(categoryId, cursorType!!)
            this@ItemViewModel._itemList.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e("ApolloException", "Failure", e)
            this@ItemViewModel._itemList.postValue(ViewState.Error("Error fetching latestOrderItemList"))
        }
    }

    fun getKeywordItemList(keyword: String, sortType: String) = viewModelScope.launch {
        var cursorType: CursorType? = null

        when (sortType) {
            "latestOrder" -> {
                cursorType = CursorType.createdAt
            }
            "deadline" -> {
                cursorType = CursorType.dueDate
            }
            //후에 수정
            "popular" -> {
                cursorType = CursorType.dueDate
            }
        }

        this@ItemViewModel._itemList.postValue(ViewState.Loading())
        try {
            val response = repository.retrieveKeywordItemList(keyword, cursorType!!)
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

    fun updateItem(itemId: Int, updateItem: ItemUpdateInput, description: String) =
        viewModelScope.launch {
            _updateItem.postValue(ViewState.Loading())
            try {
                val response = repository.updateItem(itemId, updateItem, description)
                _updateItem.postValue(ViewState.Success(response))
            } catch (e: ApolloHttpException) {
                Log.e("ApolloException", "Failure", e)
                _updateItem.postValue(ViewState.Error("Error update item status"))
            }
        }

}
