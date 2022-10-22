package com.alexk.bidit.presentation.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexk.bidit.GetBiddingInfoQuery
import com.alexk.bidit.GetMyBiddingInfoQuery
import com.alexk.bidit.common.util.value.BidStatus
import com.alexk.bidit.common.util.value.ViewState
import com.alexk.bidit.domain.repository.BiddingRepository
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloHttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class BiddingViewModel @Inject constructor(private val repository: BiddingRepository) :
    ViewModel() {
    private val _biddingInfo by lazy { MutableLiveData<ViewState<ApolloResponse<GetBiddingInfoQuery.Data>>>() }
    val biddingInfo get() = _biddingInfo

    private val _bidCompleteInfo by lazy { MutableLiveData<ViewState<BidStatus>>() }
    val bidCompleteInfo get() = _bidCompleteInfo

    private val _myBiddingInfo by lazy { MutableLiveData<ViewState<ApolloResponse<GetMyBiddingInfoQuery.Data>>>() }
    val myBiddingInfo get() = _myBiddingInfo


    fun getMyBiddingInfo() = viewModelScope.launch {
        _myBiddingInfo.postValue(ViewState.Loading())
        try {
            val response = repository.retrieveMyBiddingInfo()
            _myBiddingInfo.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e("ApolloException", "Failure", e)
            _myBiddingInfo.postValue(ViewState.Error("Error get my bidding info"))
        }
    }

    fun retrieveBiddingInfo(id: Int) = viewModelScope.launch {
        _biddingInfo.postValue(ViewState.Loading())
        try {
            val response = repository.retrieveBiddingInfo(id)
            _biddingInfo.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e("ApolloException", "Failure", e)
            _biddingInfo.postValue(ViewState.Error("Error fetching id"))
        }
    }

    fun controlBid(itemId: Int, bidPrice: Int, status: BidStatus) = viewModelScope.launch {
        _bidCompleteInfo.postValue(ViewState.Loading())
        try {
            val response = repository.controlBid(itemId, bidPrice, status.status)
            _bidCompleteInfo.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            _bidCompleteInfo.postValue(ViewState.Error(e.message))
        }
    }
}

