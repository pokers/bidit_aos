package com.alexk.bidit.presentation.ui.selling

import android.os.Bundle
import android.view.View
import com.alexk.bidit.R
import com.alexk.bidit.databinding.FragmentSearchKeywordBinding
import com.alexk.bidit.databinding.FragmentSellingBinding
import com.alexk.bidit.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SellingFragment:BaseFragment<FragmentSellingBinding>(R.layout.fragment_selling) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun init() {
        TODO("Not yet implemented")
    }

    override fun initEvent() {
        TODO("Not yet implemented")
    }

}
