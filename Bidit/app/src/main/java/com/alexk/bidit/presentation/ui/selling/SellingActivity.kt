package com.alexk.bidit.presentation.ui.selling

import com.alexk.bidit.databinding.ActivitySellingBinding
import com.alexk.bidit.presentation.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import android.os.Bundle
import androidx.navigation.ui.setupWithNavController
import com.alexk.bidit.R
import com.alexk.bidit.databinding.ActivityHomeBinding
import com.alexk.bidit.presentation.base.BaseActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SellingActivity :BaseActivity<ActivitySellingBinding>(R.layout.activity_selling,R.id.nav_selling_fragment){
    override fun init() {
        TODO("Not yet implemented")
    }

    override fun initEvent() {
        TODO("Not yet implemented")
    }

    override fun onBackPressed() {
        val f = getCurrentFragment()
        if (f is SellingFragment) {
            finish()
            return
        }

        if (!navController.navigateUp()) {
            super.onBackPressed()
        }
    }
}