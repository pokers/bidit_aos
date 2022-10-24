package com.alexk.bidit.presentation.ui.selling

import android.os.Bundle
import android.util.Log
import com.alexk.bidit.databinding.ActivitySellingBinding
import dagger.hilt.android.AndroidEntryPoint
import com.alexk.bidit.R
import com.alexk.bidit.common.util.value.SAVE_SELLING_INFO
import com.alexk.bidit.domain.entity.selling.SellingEntity
import com.alexk.bidit.presentation.base.BaseActivity
import com.alexk.bidit.presentation.ui.selling.SellingActivity.Companion.SELLING_INFO
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SellingActivity :
    BaseActivity<ActivitySellingBinding>(R.layout.activity_selling, R.id.nav_selling_fragment) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SELLING_INFO = SellingEntity(imgUrlList = mutableListOf())
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

    companion object {
        lateinit var SELLING_INFO: SellingEntity
    }
}