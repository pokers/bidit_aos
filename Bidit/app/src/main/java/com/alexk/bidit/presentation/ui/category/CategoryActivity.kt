package com.alexk.bidit.presentation.ui.category

import com.alexk.bidit.R
import com.alexk.bidit.databinding.ActivityHomeBinding
import com.alexk.bidit.presentation.base.BaseActivity

class CategoryActivity:BaseActivity<ActivityHomeBinding>(R.layout.activity_house,R.id.nav_house_fragment) {
    override fun init() {

    }

    override fun initEvent() {

    }

    override fun onBackPressed() {
        val f = getCurrentFragment()
        if (f is CategoryFragment) {
            finishAffinity()
            return
        }

        if (!navController.navigateUp()) {
            super.onBackPressed()
        }
    }
}