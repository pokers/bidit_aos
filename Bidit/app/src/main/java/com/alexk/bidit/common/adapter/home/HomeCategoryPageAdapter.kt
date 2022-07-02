package com.alexk.bidit.common.adapter.home

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alexk.bidit.presentation.ui.home.HomeCategoryFragmentTemp
import com.alexk.bidit.tempResponse.TempHomeResponse
import java.util.ArrayList

class HomeCategoryPageAdapter(
    fragmentActivity: Fragment,
    private val list: List<ArrayList<TempHomeResponse>>
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = list.size

    override fun createFragment(position: Int): Fragment {
        val fragment = HomeCategoryFragmentTemp()
        fragment.arguments = Bundle().apply {
            putParcelableArrayList("listData", list[position] as ArrayList<out Parcelable>?)
        }
        return fragment
    }
}