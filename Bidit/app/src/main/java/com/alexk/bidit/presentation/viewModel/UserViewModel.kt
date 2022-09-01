package com.alexk.bidit.presentation.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexk.bidit.*
import com.alexk.bidit.domain.repository.UserRepository
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.type.MembershipStatus
import com.alexk.bidit.type.User
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloHttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val _myInfo by lazy { MutableLiveData<ViewState<ApolloResponse<GetMyInfoQuery.Data>>>() }
    val myInfo get() = _myInfo

    private val _pushToken by lazy { MutableLiveData<ViewState<ApolloResponse<UpdatePushTokenMutation.Data>>>() }
    val pushToken get() = _pushToken

    private val _userStatusInfo by lazy { MutableLiveData<ViewState<ApolloResponse<DeleteUserInfoMutation.Data>>>() }
    val userStatusInfo get() = _userStatusInfo

    private val _updateUserInfo by lazy { MutableLiveData<ViewState<ApolloResponse<UpdateUserInfoMutation.Data>>>() }
    val updateUserInfo get() = _updateUserInfo

    private val _alarmStatus by lazy { MutableLiveData<ViewState<ApolloResponse<SetUserAlarmMutation.Data>>>() }
    val alarmStatus get() = _alarmStatus

    private val _addUserInfo by lazy { MutableLiveData<ViewState<ApolloResponse<PostMyInfoMutation.Data>>>() }
    val addUserInfo get() = _addUserInfo

    fun addUser() = viewModelScope.launch {
        _addUserInfo.postValue(ViewState.Loading())
        try{
            val response = repository.addUserInfo()
            _addUserInfo.postValue(ViewState.Success(response))
        }catch (e: ApolloHttpException){
            Log.e("ApolloException", "Failure", e)
            _addUserInfo.postValue(ViewState.Error("add alarm error"))
        }
    }

    fun addAlarm(userId : Int, status : Int) = viewModelScope.launch {
        _alarmStatus.postValue(ViewState.Loading())
        try{
            val response = repository.addAlarm(userId, status)
            _alarmStatus.postValue(ViewState.Success(response))
        }catch (e: ApolloHttpException){
            Log.e("ApolloException", "Failure", e)
            _alarmStatus.postValue(ViewState.Error("add alarm error"))
        }
    }

    fun updateUserState(status : Int) = viewModelScope.launch {
        _userStatusInfo.postValue(ViewState.Loading())
        try {
            when(status){
                0 -> {
                    val response = repository.updateUserStatus(MembershipStatus.VALID)
                    _userStatusInfo.postValue(ViewState.Success(response))
                }
                1 ->{
                    val response = repository.updateUserStatus(MembershipStatus.INVALID)
                    _userStatusInfo.postValue(ViewState.Success(response))
                }
            }
        }catch (e:ApolloHttpException){
            Log.e("ApolloException", "Failure", e)
            _myInfo.postValue(ViewState.Error("update user error"))
        }
    }

    fun getMyInfo() = viewModelScope.launch {
        _myInfo.postValue(ViewState.Loading())
        try {
            val response = repository.getMyInfo()
            _myInfo.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e("ApolloException", "Failure", e)
            _myInfo.postValue(ViewState.Error("Error fetching id"))
        }
    }

    fun updatePushToken(status:Int?, pushToken:String) = viewModelScope.launch {
        _pushToken.postValue(ViewState.Loading())
        try{
            val response = repository.updatePushToken(status, pushToken)
            _pushToken.postValue(ViewState.Success(response))
        }catch (e: ApolloHttpException){
            Log.e("ApolloException", "Failure", e)
            _pushToken.postValue(ViewState.Error("Error update push token"))
        }
    }

    fun updateUserInfo(nickname : String, profileImg : String?) = viewModelScope.launch {
        _updateUserInfo.postValue(ViewState.Loading())
        try{
            val response = repository.updateUserInfo(nickname, profileImg)
            _updateUserInfo.postValue(ViewState.Success(response))
        }catch (e: ApolloHttpException){
            Log.d("UPDATE_USER_INFO","Failure",e)
            _updateUserInfo.postValue(ViewState.Error("Error update user info"))
        }
    }

}