package com.alexk.bidit.common.adapter.home

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alexk.bidit.presentation.ui.home.HomeCategoryFragment
import com.alexk.bidit.tempResponse.HomeResponse
import java.util.ArrayList

class HomeCategoryPageAdapter(
    fragmentActivity: Fragment,
    private val list: List<ArrayList<HomeResponse>>
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = list.size

    override fun createFragment(position: Int): Fragment {
        val fragment = HomeCategoryFragment()
        fragment.arguments = Bundle().apply {
            putParcelableArrayList("listData", list[position] as ArrayList<out Parcelable>?)
        }
        return fragment
    }
}