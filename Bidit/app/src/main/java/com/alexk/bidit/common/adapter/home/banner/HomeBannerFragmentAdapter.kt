package com.alexk.bidit.common.adapter.home.banner

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alexk.bidit.common.util.value.KeyConstants.FRAGMENT_KEY_HOME_BANNER_POSITION
import com.alexk.bidit.presentation.ui.home.banner.HomeBannerFragment

class HomeBannerFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = BANNER_SIZE

    override fun createFragment(position: Int): Fragment {
        val bannerFragment = HomeBannerFragment().apply {
            arguments = Bundle().apply {
                this.putInt(FRAGMENT_KEY_HOME_BANNER_POSITION, position % BANNER_SIZE)
            }
        }
        return bannerFragment
    }
    companion object{
        private const val BANNER_SIZE = 2
    }
}
