package com.alexk.bidit.presentation.ui.bid

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.alexk.bidit.R
import com.alexk.bidit.databinding.ActivitiyBiddingBinding
import com.alexk.bidit.presentation.base.BaseActivity
import com.alexk.bidit.presentation.ui.bid.complete.BiddingCompleteFragment
import com.alexk.bidit.presentation.ui.home.HomeActivity
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

        if(f is BiddingCompleteFragment){
            finishAffinity()
            startActivity(Intent(this,HomeActivity::class.java))
        }

        if (!navController.navigateUp()) {
            super.onBackPressed()
        }
    }
}