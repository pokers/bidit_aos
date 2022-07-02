package com.alexk.bidit.presentation.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexk.bidit.GetItemInfoQuery
import com.alexk.bidit.GetMyInfoQuery
import com.alexk.bidit.domain.repository.MerchandiseRepository
import com.apollographql.apollo3.api.ApolloResponse
import com.alexk.bidit.di.ViewState
import com.apollographql.apollo.exception.ApolloException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MerchandiseViewModel @Inject constructor(private val repository: MerchandiseRepository) :
    ViewModel() {
    //일단 아이템 객체 한개
    private val _item by lazy { MutableLiveData<ViewState<ApolloResponse<GetItemInfoQuery.Data>>>() }
    val item get() = _item

    fun getItemInfo(id : Int) = viewModelScope.launch {
        _item.postValue(ViewState.Loading())
        try {
            val response = repository.getItemList(id)
            _item.postValue(ViewState.Success(response))
        } catch (e: ApolloException) {
            Log.e("ApolloException", "Failure", e)
            _item.postValue(ViewState.Error("Error fetching id"))
        }
    }
}