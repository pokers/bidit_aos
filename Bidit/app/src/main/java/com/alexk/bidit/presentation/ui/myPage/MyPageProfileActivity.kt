package com.alexk.bidit.presentation.ui.myPage

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.common.dialog.LoadingDialog
import com.alexk.bidit.databinding.ActivityMyPageProfileModifyBinding
import com.alexk.bidit.common.util.view.ViewState
import com.alexk.bidit.presentation.ui.myPage.dialog.MyPageEditProfileDialog
import com.alexk.bidit.presentation.viewModel.UserViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MyPageProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyPageProfileModifyBinding
    private val userViewModel by viewModels<UserViewModel>()
    private var imgUrl = ""
    private var nickname = ""
    private lateinit var loadingDialog : LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_page_profile_modify)
        setContentView(binding.root)
        init()
        initEvent()
        observeUserUpdate()
    }

    private fun init() {
        loadingDialog = LoadingDialog(this)
        imgUrl = intent.getStringExtra("imgUrl").toString()
        nickname = intent.getStringExtra("nickname").toString()
        binding.apply {
            Glide.with(applicationContext)
                .load(imgUrl)
                .error(R.drawable.ic_notification)
                .into(ivProfileImg)


            editNickname.setText(nickname)
        }
    }

    private fun initEvent() {
        binding.apply {
            ivCamera.setOnClickListener {
                val dialog = MyPageEditProfileDialog()
                dialog.show(supportFragmentManager, dialog.tag)
            }

            btnComplete.setOnClickListener {
                if (editNickname.text.toString() == "") {
                    tvNicknameError.text = "닉네임을 작성해주세요."
                    tvNicknameError.visibility = View.VISIBLE
                } else {
                    if (editNickname.text.toString().length > 8) {
                        tvNicknameError.text = "닉네임은 8자 이내로 작성해주세요."
                        tvNicknameError.visibility = View.VISIBLE
                    } else {
                        //프로필 변경
                        nickname = editNickname.text.toString()
                        userViewModel.updateUserInfo(nickname, imgUrl)
                    }
                }
            }
        }
    }

    private fun observeUserUpdate() {
        userViewModel.updateUserInfo.observe(this) { response ->
            when(response){
                is ViewState.Loading -> {
                    loadingDialog.show()
                    Log.d("UPDATE_USER_INFO","Loading")
                }
                is ViewState.Success -> {
                    loadingDialog.dismiss()
                    val result = response.value?.data?.updateUser
                    Log.d("UPDATE_USER_INFO","Success : ${result?.nickname}")
                    Toast.makeText(this,"닉네임이 변경되었습니다.",Toast.LENGTH_SHORT).show()
                    finish()
                }
                is ViewState.Error -> {
                    loadingDialog.dismiss()
                    Log.d("UPDATE_USER_INFO","Error")
                }
            }
        }
    }
}