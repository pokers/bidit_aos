package com.alexk.bidit.common.adapter.bidding

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alexk.bidit.common.util.value.FRAGMENT_KEY_ITEM_IMG_URL
import com.alexk.bidit.domain.entity.item.img.ItemImgEntity
import com.alexk.bidit.presentation.ui.bid.img.BiddingMerchandiseImgFragment

class BiddingMerchandiseImgPageAdapter(
    fragment: Fragment,
    private val imgList: List<ItemImgEntity>
) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return imgList.size
    }

    override fun createFragment(position: Int): Fragment {
        return BiddingMerchandiseImgFragment().apply {
            arguments = Bundle().apply {
                putString(FRAGMENT_KEY_ITEM_IMG_URL, imgList[position].imgUrl)
            }
        }
    }
}