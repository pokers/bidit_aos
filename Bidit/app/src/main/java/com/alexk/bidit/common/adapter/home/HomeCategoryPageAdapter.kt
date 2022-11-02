package com.alexk.bidit.common.adapter.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alexk.bidit.common.util.value.KeyConstants.FRAGMENT_KEY_ITEM_CATEGORY_TYPE
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
        return when (position) {
            0 -> {
                HomeCategoryFragment().apply {
                    arguments = Bundle().apply {
                        this.putString(FRAGMENT_KEY_ITEM_CATEGORY_TYPE, "DEADLINE")
                    }
                }
            }
            else -> {
                HomeCategoryFragment().apply {
                    arguments = Bundle().apply {
                        this.putString(FRAGMENT_KEY_ITEM_CATEGORY_TYPE, "LATEST_ORDER")
                    }
                }
            }
        }
    }

    companion object {
        private const val PAGE_COUNT = 2
    }
}