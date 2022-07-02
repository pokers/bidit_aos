package com.alexk.bidit.common.adapter.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alexk.bidit.presentation.ui.home.HomeCategoryFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class HomeCategoryPageAdapter(
    fragmentActivity: Fragment
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 5

    override fun createFragment(position: Int): Fragment {
        //프로모션이나 다른 UI가 나온다면 수정해주세요.
        return when(position){
            0 -> {
                //ending soon
                val fragment = HomeCategoryFragment()
                fragment.arguments = Bundle().apply {
                    this.putString("sortType","deadline")
                }
                return fragment
            }
            1 -> {
                val fragment = HomeCategoryFragment()
                fragment.arguments = Bundle().apply {
                    this.putString("sortType","latestOrder")
                }
                return fragment
            }
            2 -> {
                val fragment = HomeCategoryFragment()
                fragment.arguments = Bundle().apply {
                    this.putString("sortType","latestOrder")
                }
                return fragment
            }
            else -> {
                val fragment = HomeCategoryFragment()
                fragment.arguments = Bundle().apply {
                    this.putString("sortType","latestOrder")
                }
                return fragment
            }
        }
    }
}