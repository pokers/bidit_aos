package com.alexk.bidit.common.adapter.bidding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alexk.bidit.presentation.ui.bidding.BiddingMerchandiseImgFragment

class BiddingMerchandiseImgPageAdapter(fragment: Fragment, private val imgList: List<Int>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount() = imgList.size

    override fun createFragment(position: Int): Fragment {
        return BiddingMerchandiseImgFragment(imgList[position])
    }
}