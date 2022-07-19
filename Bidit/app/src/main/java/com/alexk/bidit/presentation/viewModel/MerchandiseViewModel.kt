package com.alexk.bidit.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexk.bidit.*
import com.alexk.bidit.domain.repository.MerchandiseRepository
import com.apollographql.apollo3.api.ApolloResponse
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.type.CursorType
import com.alexk.bidit.type.ItemAddInput
import com.alexk.bidit.type.ItemDescription
import com.apollographql.apollo3.exception.ApolloHttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MerchandiseViewModel @Inject constructor(private val repository: MerchandiseRepository) :
    ViewModel() {

    private val _itemList by lazy { MutableLiveData<ViewState<ApolloResponse<GetItemListQuery.Data>>>() }
    val itemList get() = _itemList

    private val _itemInfo by lazy { MutableLiveData<ViewState<ApolloResponse<GetItemInfoQuery.Data>>>() }
    val itemInfo get() = _itemInfo

    private val _itemStatus by lazy { MutableLiveData<ViewState<ApolloResponse<UpdateItemStatusMutation.Data>>>() }
    val itemStatus get() = _itemStatus

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
            this@MerchandiseViewModel._itemInfo.postValue(ViewState.Error("Error fetching ItemInfo"))
        }
    }

    fun getMyItemList(id: Int) = viewModelScope.launch {
        this@MerchandiseViewModel._itemList.postValue(ViewState.Loading())
        try {
            val response = repository.retrieveMyItemList(userId = id)
            this@MerchandiseViewModel._itemList.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e("MY_ITEM_LIST", "Error GET my list")
            this@MerchandiseViewModel._itemList.postValue(ViewState.Error("Error fetching ItemList"))
        }
    }

    fun getSortTypeItemList(sortType: String) = viewModelScope.launch {
        this@MerchandiseViewModel._itemList.postValue(ViewState.Loading())
        try {
            when (sortType) {
                "latestOrder" -> {
                    val response = repository.retrieveCursorTypeItemList(CursorType.createdAt)
                    this@MerchandiseViewModel._itemList.postValue(ViewState.Success(response))
                }
                "deadline" -> {
                    this@MerchandiseViewModel._itemList.postValue(ViewState.Loading())
                    val response = repository.retrieveCursorTypeItemList(CursorType.dueDate)
                    this@MerchandiseViewModel._itemList.postValue(ViewState.Success(response))
                }
            }
        } catch (e: ApolloHttpException) {
            Log.e("ApolloException", "Failure", e)
            this@MerchandiseViewModel._itemList.postValue(ViewState.Error("Error fetching ItemList"))
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
        this@MerchandiseViewModel._itemList.postValue(ViewState.Loading())
        try {
            val response = repository.retrieveCategoryItemList(categoryId, cursorType!!)
            this@MerchandiseViewModel._itemList.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e("ApolloException", "Failure", e)
            this@MerchandiseViewModel._itemList.postValue(ViewState.Error("Error fetching latestOrderItemList"))
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

        this@MerchandiseViewModel._itemList.postValue(ViewState.Loading())
        try {
            val response = repository.retrieveKeywordItemList(keyword, cursorType!!)
            this@MerchandiseViewModel._itemList.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e("ApolloException", "Failure", e)
            this@MerchandiseViewModel._itemList.postValue(ViewState.Error("Error fetching latestOrderItemList"))
        }
    }

    fun updateItemStatus(itemId: Int, status: Int) = viewModelScope.launch {
        _itemStatus.postValue(ViewState.Loading())
        try {
            val response = repository.updateItemStatus(itemId, status)
            _itemStatus.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e("ApolloException", "Failure", e)
            _itemStatus.postValue(ViewState.Error("Error update item status"))
        }
    }
}
