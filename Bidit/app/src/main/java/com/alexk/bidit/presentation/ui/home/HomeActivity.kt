package com.alexk.bidit.presentation.ui.home

import android.os.Bundle
import android.widget.AdapterView
import androidx.navigation.ui.setupWithNavController
import com.alexk.bidit.R
import com.alexk.bidit.databinding.ActivityHomeBinding
import com.alexk.bidit.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class HomeActivity :
    BaseActivity<ActivityHomeBinding>(R.layout.activity_home, R.id.nav_home_fragment) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBottomNavigation()
    }

    override fun onBackPressed() {
        val f = getCurrentFragment()
        if (f is HomeFragment) {
            finishAffinity()
            return
        }

        if (!navController.navigateUp()) {
            super.onBackPressed()
        }
    }

    private fun setBottomNavigation() {
        binding.apply {
            navHomeBottom.setupWithNavController(navController)
        }
    }
}