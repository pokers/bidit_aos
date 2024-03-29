package com.alexk.bidit.common.adapter.myTrade

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alexk.bidit.presentation.ui.myTrade.MyTradeItemListFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MyTradeCategoryPageAdapter(fragmentActivity: Fragment) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): MyTradeItemListFragment {
        val fragment = MyTradeItemListFragment()
        return when (position) {
            0 -> {
                fragment.arguments = Bundle().apply {
                    this.putString("listType", "sold")
                }
                fragment
            }
            else -> {
                fragment.arguments = Bundle().apply {
                    this.putString("listType", "bid")
                }
                fragment
            }
        }
    }
}