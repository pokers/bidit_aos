package com.alexk.bidit.presentation.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexk.bidit.*
import com.alexk.bidit.common.util.ErrorCouldNotAdd
import com.alexk.bidit.common.util.ErrorInvalidToken
import com.alexk.bidit.common.util.ErrorNotMatchedArticle
import com.alexk.bidit.domain.repository.UserRepository
import com.alexk.bidit.common.view.ViewState
import com.alexk.bidit.domain.entity.user.UserBasicEntity
import com.alexk.bidit.type.MembershipStatus
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloHttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val _myInfo by lazy { MutableLiveData<ViewState<UserBasicEntity>>() }
    val myInfo get() = _myInfo

    private val _addUserInfo by lazy { MutableLiveData<ViewState<Int>>() }
    val addUserInfo get() = _addUserInfo

    private val _pushToken by lazy { MutableLiveData<ViewState<Boolean>>() }
    val pushToken get() = _pushToken

    private val _userStatusInfo by lazy { MutableLiveData<ViewState<UserBasicEntity>>() }
    val userStatusInfo get() = _userStatusInfo

    private val _updateUserInfo by lazy { MutableLiveData<ViewState<UserBasicEntity>>() }
    val updateUserInfo get() = _updateUserInfo

    private val _alarmStatus by lazy { MutableLiveData<ViewState<ApolloResponse<SetUserAlarmMutation.Data>>>() }
    val alarmStatus get() = _alarmStatus


    fun addUser() = viewModelScope.launch {
        _addUserInfo.postValue(ViewState.Loading())
        try {
            val response = repository.addUserInfo()
            _addUserInfo.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e(TAG, "addUser: ${e.message}")
            _addUserInfo.postValue(ViewState.Error(ErrorCouldNotAdd))
        }
    }

    fun getMyInfo() = viewModelScope.launch {
        _myInfo.postValue(ViewState.Loading())
        try {
            val response = repository.getMyInfo()
            _myInfo.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            _pushToken.postValue(ViewState.Error(ErrorInvalidToken))
        }
    }

    fun updatePushToken(status: Int?, pushToken: String) = viewModelScope.launch {
        _pushToken.postValue(ViewState.Loading())
        try {
            val response = repository.updatePushToken(status, pushToken)
            _pushToken.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e(TAG, "updatePushToken: ${e.message}")
            _pushToken.postValue(ViewState.Error(ErrorInvalidToken))
        }
    }

    fun updateUserState(status: Int) = viewModelScope.launch {
        _userStatusInfo.postValue(ViewState.Loading())
        try {
            when (status) {
                0 -> {
                    val response = repository.updateUserStatus(MembershipStatus.VALID)
                    _userStatusInfo.postValue(ViewState.Success(response))
                }
                1 -> {
                    val response = repository.updateUserStatus(MembershipStatus.INVALID)
                    _userStatusInfo.postValue(ViewState.Success(response))
                }
            }
        } catch (e: ApolloHttpException) {
            Log.e(TAG, "updateUserState: ${e.message}")
            _myInfo.postValue(ViewState.Error(ErrorNotMatchedArticle))
        }
    }

    fun addAlarm(userId: Int, status: Int) = viewModelScope.launch {
        _alarmStatus.postValue(ViewState.Loading())
        try {
            val response = repository.addAlarm(userId, status)
            _alarmStatus.postValue(ViewState.Success(response))
        } catch (e: ApolloHttpException) {
            Log.e("ApolloException", "Failure", e)
            _alarmStatus.postValue(ViewState.Error("add alarm error"))
        }
    }


    fun updateUserNickNameAndProfileImg(nickname: String, profileImg: String?) =
        viewModelScope.launch {
            _updateUserInfo.postValue(ViewState.Loading())
            try {
                val response = repository.updateUserInfo(nickname, profileImg)
                _updateUserInfo.postValue(ViewState.Success(response))
            } catch (e: ApolloHttpException) {
                Log.d("UPDATE_USER_INFO", "Failure", e)
                _updateUserInfo.postValue(ViewState.Error("Error update user info"))
            }
        }

    companion object {
        private const val TAG = "UserViewModel..."
    }
}