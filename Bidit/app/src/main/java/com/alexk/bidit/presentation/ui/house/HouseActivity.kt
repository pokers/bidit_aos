package com.alexk.bidit.presentation.ui.house

import com.alexk.bidit.R
import com.alexk.bidit.databinding.ActivityHomeBinding
import com.alexk.bidit.presentation.base.BaseActivity
import com.alexk.bidit.presentation.ui.home.HomeFragment

class HouseActivity:BaseActivity<ActivityHomeBinding>(R.layout.activity_house,R.id.nav_house_fragment) {
    override fun init() {

    }

    override fun initEvent() {

    }

    override fun onBackPressed() {
        val f = getCurrentFragment()
        if (f is HouseFragment) {
            finishAffinity()
            return
        }

        if (!navController.navigateUp()) {
            super.onBackPressed()
        }
    }
}