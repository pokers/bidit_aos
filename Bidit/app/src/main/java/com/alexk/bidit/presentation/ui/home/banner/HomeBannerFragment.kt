package com.alexk.bidit.presentation.ui.home.banner

import android.os.Bundle
import android.view.View
import com.alexk.bidit.R
import com.alexk.bidit.common.util.value.FRAGMENT_KEY_HOME_BANNER_POSITION
import com.alexk.bidit.databinding.FragmentHomeBannerBinding
import com.alexk.bidit.presentation.base.BaseFragment

class HomeBannerFragment : BaseFragment<FragmentHomeBannerBinding>(R.layout.fragment_home_banner) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bannerPosition = arguments?.getInt(FRAGMENT_KEY_HOME_BANNER_POSITION)
        setBannerImg(bannerPosition!!)
    }

    private fun setBannerImg(bannerListPosition: Int) {
        binding.ivBanner.setImageResource(bannerImgList[bannerListPosition])
    }

    companion object {
        private val bannerImgList = listOf(R.drawable.bg_temp_banner1, R.drawable.bg_temp_banner_2)
    }
}