package com.alexk.bidit.presentation.ui.bidding

import android.os.Bundle
import com.alexk.bidit.R
import com.alexk.bidit.databinding.ActivitiyBiddingBinding
import com.alexk.bidit.presentation.base.BaseActivity
import com.alexk.bidit.presentation.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class BiddingActivity:BaseActivity<ActivitiyBiddingBinding>(R.layout.activitiy_bidding,R.id.nav_bidding_fragment) {

    override fun onBackPressed() {
        val f = getCurrentFragment()
        if (f is BiddingFragment) {
            finish()
            return
        }

        if (!navController.navigateUp()) {
            super.onBackPressed()
        }
    }

    override fun init() {
        TODO("Not yet implemented")
    }

    override fun initEvent() {
        TODO("Not yet implemented")
    }
}