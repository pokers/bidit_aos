package com.alexk.bidit.presentation.ui.myPage

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.alexk.bidit.R
import com.alexk.bidit.data.sharedPreference.TokenManager
import com.alexk.bidit.databinding.ActivityMyPageAlarmBinding
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.presentation.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MyPageAlarmActivity :AppCompatActivity() {

    private lateinit var binding : ActivityMyPageAlarmBinding
    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_page_alarm)
        setContentView(binding.root)

        init()
        initEvent()
    }

    private fun init(){
        observePushToken()
    }

    private fun initEvent(){
        binding.apply {
            cbAllPushAlarm.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    userViewModel.updatePushToken(0,TokenManager(applicationContext).getPushToken())
                }
                else{
                    userViewModel.updatePushToken(1,TokenManager(applicationContext).getPushToken())
                }
            }
        }
    }

    private fun observePushToken(){
        userViewModel.myInfo.observe(this) { response ->
            when(response){
                is ViewState.Loading -> {
                    Log.d("PushToken","Loading")
                }
                is ViewState.Success -> {
                    Log.d("PushToken","Success")
                    val result = response.value?.data?.me?.pushToken?.status
                    binding.cbAllPushAlarm.isChecked = result == 0
                }
                is ViewState.Error -> {
                    Log.d("PushToken","Error")
                }
            }
        }
    }
}