package com.alexk.bidit.common.adapter.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alexk.bidit.presentation.ui.home.HomeBannerFragment
import com.alexk.bidit.presentation.ui.home.HomeCategoryFragment

class CommonBannerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        val fragment = HomeBannerFragment()
        fragment.arguments = Bundle().apply {
            this.putInt("bannerPosition", position % 3)
        }
        return fragment
    }
}
