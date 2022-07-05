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
import com.apollographql.apollo3.exception.ApolloHttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MerchandiseViewModel @Inject constructor(private val repository: MerchandiseRepository) :
    ViewModel() {

    private val _cursorTypeItemList by lazy { MutableLiveData<ViewState<ApolloResponse<GetItemListQuery.Data>>>() }
    val cursorTypeItemList: LiveData<ViewState<ApolloResponse<GetItemListQuery.Data>>> get() = _cursorTypeItemList

    private val _categoryItemList by lazy { MutableLiveData<ViewState<ApolloResponse<GetItemListQuery.Data>>>() }
    val categoryItemList: LiveData<ViewState<ApolloResponse<GetItemListQuery.Data>>> get() = _categoryItemList

    fun getSortTypeItemList(sortType: String) = viewModelScope.launch {
        _cursorTypeItemList.postValue(ViewState.Loading())
        try {
            when (sortType) {
                "latestOrder" -> {
                    val response = repository.retrieveCursorTypeItemList(CursorType.createdAt)
                    _cursorTypeItemList.postValue(ViewState.Success(response))
                }
                "deadline" -> {
                    _cursorTypeItemList.postValue(ViewState.Loading())
                    val response = repository.retrieveCursorTypeItemList(CursorType.dueDate)
                    _cursorTypeItemList.postValue(ViewState.Success(response))
                }
            }
        } catch (e: ApolloHttpException) {
            Log.e("ApolloException", "Failure", e)
            _cursorTypeItemList.postValue(ViewState.Error("Error fetching ItemList"))
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
        }
        _categoryItemList.postValue(ViewState.Loading())
        try {
            val response = repository.retrieveCategoryItemList(categoryId, cursorType!!)
            _categoryItemList.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e("ApolloException", "Failure", e)
            _categoryItemList.postValue(ViewState.Error("Error fetching latestOrderItemList"))
        }
    }

    fun getKeywordItemList(keyword: String) = viewModelScope.launch {
        _categoryItemList.postValue(ViewState.Loading())
        try {
            val response = repository.retrieveKeywordItemList(keyword)
            _categoryItemList.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e("ApolloException", "Failure", e)
            _categoryItemList.postValue(ViewState.Error("Error fetching latestOrderItemList"))
        }
    }
}