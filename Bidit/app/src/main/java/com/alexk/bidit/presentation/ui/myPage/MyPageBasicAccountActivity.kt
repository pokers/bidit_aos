package com.alexk.bidit.presentation.ui.myPage

import android.os.Bundle
import com.alexk.bidit.R
import com.alexk.bidit.databinding.ActivityMyPageBasicInfoBinding
import com.alexk.bidit.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MyPageBasicAccountActivity :
    BaseActivity<ActivityMyPageBasicInfoBinding>(R.layout.activity_my_page_basic_info, R.id.nav_my_page_basic_info_fragment) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        initEvent()
    }

    override fun init() {

    }

    override fun initEvent() {

    }

    override fun onBackPressed() {
        val f = getCurrentFragment()
        if (f is MyPageAccountInfoFragment) {
            finish()
            return
        }

        if(f is MyPageSignOutCompleteFragment){
            finishAffinity()
            return
        }

        if (!navController.navigateUp()) {
            super.onBackPressed()
        }
    }
}