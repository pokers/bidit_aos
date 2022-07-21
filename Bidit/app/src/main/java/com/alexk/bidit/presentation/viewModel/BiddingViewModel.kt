package com.alexk.bidit.presentation.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexk.bidit.DoBidMutation
import com.alexk.bidit.GetBiddingInfoQuery
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.domain.repository.BiddingRepository
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloHttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class BiddingViewModel @Inject constructor(private val repository: BiddingRepository):ViewModel() {
    private val _biddingInfo by lazy { MutableLiveData<ViewState<ApolloResponse<GetBiddingInfoQuery.Data>>>() }
    val biddingInfo get() = _biddingInfo

    private val _bidCompleteInfo by lazy { MutableLiveData<ViewState<ApolloResponse<DoBidMutation.Data>>>() }
    val bidCompleteInfo get() = _bidCompleteInfo

    fun retrieveBiddingInfo(id : Int) = viewModelScope.launch {
        _biddingInfo.postValue(ViewState.Loading())
        try {
            val response = repository.retrieveBiddingInfo(id)
            _biddingInfo.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e("ApolloException", "Failure", e)
            _biddingInfo.postValue(ViewState.Error("Error fetching id"))
        }
    }

    fun controlBid(itemId :Int, bidPrice:Int, status:Int) = viewModelScope.launch {
        _bidCompleteInfo.postValue(ViewState.Loading())
        try {
            val response = repository.controlBid(itemId,bidPrice,status)
            _bidCompleteInfo.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e("ApolloException", "Failure", e)
            _bidCompleteInfo.postValue(ViewState.Error("Error do bid"))
        }
    }
}

