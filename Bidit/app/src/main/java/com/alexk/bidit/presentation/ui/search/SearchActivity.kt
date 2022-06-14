package com.alexk.bidit.presentation.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.alexk.bidit.R
import com.alexk.bidit.data.sharedPreference.SearchKeywordManager
import com.alexk.bidit.databinding.ActivitySearchBinding
import com.alexk.bidit.presentation.base.BaseActivity

class SearchActivity :
    BaseActivity<ActivitySearchBinding>(R.layout.activity_search, R.id.nav_search_fragment),
    View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed() {
        val f = getCurrentFragment()
        if (f is SearchKeywordFragment) {
            finish()
            return
        }

        if (!navController.navigateUp()) {
            super.onBackPressed()
        }
    }

    override fun init() {

    }

    override fun initEvent() {

    }

    override fun onClick(view: View?) {

    }

}