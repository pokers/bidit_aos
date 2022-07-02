package com.alexk.bidit.presentation.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexk.bidit.GetMyInfoQuery
import com.alexk.bidit.domain.repository.LoginRepository
import com.alexk.bidit.di.ViewState
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo3.api.ApolloResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    private val _id by lazy { MutableLiveData<ViewState<ApolloResponse<GetMyInfoQuery.Data>>>() }
    val id get() = _id

    fun getMyInfo() = viewModelScope.launch {
        _id.postValue(ViewState.Loading())
        try {
            val response = repository.checkToken()
            _id.postValue(ViewState.Success(response))
        } catch (e: ApolloException) {
            Log.e("ApolloException", "Failure", e)
            _id.postValue(ViewState.Error("Error fetching id"))
        }
    }
}