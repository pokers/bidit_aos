package com.alexk.bidit.common.adapter.bidding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alexk.bidit.GetItemInfoQuery
import com.alexk.bidit.presentation.ui.item.BiddingMerchandiseImgFragment

class BiddingMerchandiseImgPageAdapter(
    fragment: Fragment,
    private val imgList: List<GetItemInfoQuery.Image?>?
) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return imgList?.size ?: 0
    }

    override fun createFragment(position: Int): Fragment {
        return BiddingMerchandiseImgFragment(imgList?.get(position)?.url)
    }
}