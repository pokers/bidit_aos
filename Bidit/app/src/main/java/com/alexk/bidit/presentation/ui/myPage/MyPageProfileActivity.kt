package com.alexk.bidit.presentation.ui.myPage

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.databinding.ActivityMyPageProfileModifyBinding
import com.alexk.bidit.presentation.ui.myPage.dialog.MyPageEditProfileDialog
import com.bumptech.glide.Glide

class MyPageProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyPageProfileModifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_page_profile_modify)
        setContentView(binding.root)
        init()
        initEvent()
    }

    private fun init(){
        binding.apply {
            Glide.with(applicationContext)
                .load(intent.getStringExtra("imgUrl"))
                .into(ivProfileImg)

            editNickname.setText(intent.getStringExtra("nickname"))
        }
    }

    private fun initEvent(){
        binding.apply {
            ivCamera.setOnClickListener {
                val dialog = MyPageEditProfileDialog(this@MyPageProfileActivity)
                dialog.setCanceledOnTouchOutside(true)
                dialog.show()
                dialog.window?.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
            }

            btnComplete.setOnClickListener {
                if(editNickname.text.toString() == ""){
                    tvNicknameError.text = "닉네임을 작성해주세요."
                    tvNicknameError.visibility = View.VISIBLE
                }
                else {
                    if (editNickname.text.toString().length < 8) {
                        tvNicknameError.text = "닉네임은 8자 이내로 작성해주세요."
                        tvNicknameError.visibility = View.VISIBLE
                    }
                    else{
                        //프로필 변경
                    }
                }
            }
        }
    }
}