package com.alexk.bidit.presentation.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexk.bidit.GetBiddingInfoQuery
import com.alexk.bidit.GetMyInfoQuery
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.domain.repository.BiddingRepository
import com.alexk.bidit.domain.repository.LoginRepository
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

    fun retrieveBiddingInfo(itemId : Int) = viewModelScope.launch {
        _biddingInfo.postValue(ViewState.Loading())
        try {
            val response = repository.retrieveBiddingInfo(itemId)
            _biddingInfo.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e("ApolloException", "Failure", e)
            _biddingInfo.postValue(ViewState.Error("Error fetching id"))
        }
    }
}

