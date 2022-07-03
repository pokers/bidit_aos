package com.alexk.bidit.common.adapter.bidding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alexk.bidit.GetBiddingInfoQuery
import com.alexk.bidit.presentation.ui.bidding.BiddingMerchandiseImgFragment

class BiddingMerchandiseImgPageAdapter(
    fragment: Fragment,
    private val imgList: List<GetBiddingInfoQuery.Image?>?
) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return imgList?.size ?: 0
    }

    override fun createFragment(position: Int): Fragment {
        return BiddingMerchandiseImgFragment(imgList?.get(position)?.url)
    }
}