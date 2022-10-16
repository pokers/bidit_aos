package com.alexk.bidit.common.adapter.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alexk.bidit.common.util.value.ITEM_CATEGORY_TYPE
import com.alexk.bidit.presentation.ui.home.category.HomeCategoryFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class HomeCategoryPageAdapter(
    fragmentActivity: Fragment
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = PAGE_COUNT

    override fun createFragment(position: Int): Fragment {
        //프로모션이나 다른 UI가 나온다면 수정해주세요.
        val fragment = HomeCategoryFragment()
        when (position) {
            0 -> {
                fragment.apply {
                    arguments = Bundle().apply {
                        this.putString(ITEM_CATEGORY_TYPE, "DEADLINE")
                    }
                }
            }
            else -> {
                fragment.apply {
                    arguments = Bundle().apply {
                        this.putString(ITEM_CATEGORY_TYPE, "LATEST_ORDER")
                    }
                }
            }
        }
        return fragment
    }

    companion object {
        private const val PAGE_COUNT = 2
    }
}