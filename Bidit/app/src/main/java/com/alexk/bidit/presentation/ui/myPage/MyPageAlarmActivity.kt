package com.alexk.bidit.presentation.ui.myPage

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.databinding.ActivityMyPageAlarmBinding
import com.alexk.bidit.common.view.ViewState
import com.alexk.bidit.common.view.LoadingDialog
import com.alexk.bidit.presentation.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MyPageAlarmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPageAlarmBinding
    private val userViewModel by viewModels<UserViewModel>()
    private val loadingDialog by lazy { LoadingDialog(this) }
    private var userId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_page_alarm)
        setContentView(binding.root)

        init()
        initEvent()
    }

    private fun init() {
        observePushToken()
        userViewModel.getMyInfo()
    }

    private fun initEvent() {
        binding.apply {
            cbAllPushAlarm.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    userViewModel.addAlarm(userId,0)
                }
                else{
                    userViewModel.addAlarm(userId,1)
                }
            }
        }
    }

    private fun observePushToken() {
        userViewModel.myInfo.observe(this) { response ->
            when (response) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                    Log.d("PushToken", "Loading")
                }
                is ViewState.Success -> {
                    loadingDialog.dismiss()
                    Log.d("PushToken", "Success")
                    val result = response.value?.alarm
                    userId = response.value?.id!!
                    binding.cbAllPushAlarm.isChecked = result != null
                }
                is ViewState.Error -> {
                    loadingDialog.dismiss()
                    Log.d("PushToken", "Error")
                }
            }
        }

        userViewModel.alarmStatus.observe(this) { response ->
            when (response) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                    Log.d("PushToken", "Loading")
                }
                is ViewState.Success -> {
                    loadingDialog.dismiss()
                    Log.d("PushToken", "Success")
                    userViewModel.getMyInfo()
                }
                is ViewState.Error -> {
                    loadingDialog.dismiss()
                    Log.d("PushToken", "Error")
                }
            }
        }
    }
}