package com.alexk.bidit.presentation.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexk.bidit.GetMyInfoQuery
import com.alexk.bidit.UpdatePushTokenMutation
import com.alexk.bidit.domain.repository.UserRepository
import com.alexk.bidit.di.ViewState
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloHttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val _id by lazy { MutableLiveData<ViewState<ApolloResponse<GetMyInfoQuery.Data>>>() }
    val id get() = _id

    private val _pushToken by lazy { MutableLiveData<ViewState<ApolloResponse<UpdatePushTokenMutation.Data>>>() }
    val pushToken get() = _pushToken

    fun getMyInfo() = viewModelScope.launch {
        _id.postValue(ViewState.Loading())
        try {
            val response = repository.checkToken()
            _id.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e("ApolloException", "Failure", e)
            _id.postValue(ViewState.Error("Error fetching id"))
        }
    }

    fun updatePushToken() = viewModelScope.launch {
        _pushToken.postValue(ViewState.Loading())
        try{
            val response = repository.updatePushToken()
            _pushToken.postValue(ViewState.Success(response))
        }catch (e: ApolloHttpException){
            Log.e("ApolloException", "Failure", e)
            _pushToken.postValue(ViewState.Error("Error update push token"))
        }
    }
}