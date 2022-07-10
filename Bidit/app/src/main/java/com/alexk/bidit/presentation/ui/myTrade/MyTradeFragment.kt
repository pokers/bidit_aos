package com.alexk.bidit.presentation.ui.myTrade

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.home.HomeCategoryPageAdapter
import com.alexk.bidit.common.adapter.myTrade.MyTradeCategoryPageAdapter
import com.alexk.bidit.databinding.FragmentMyTradeBinding
import com.alexk.bidit.presentation.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MyTradeFragment : BaseFragment<FragmentMyTradeBinding>(R.layout.fragment_my_trade) {

    private val categoryList by lazy { resources.getStringArray(R.array.category_my_trade) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initEvent()
    }

    override fun init() {
        binding.apply {
            vpMyTradeList.apply {
                adapter = MyTradeCategoryPageAdapter(this@MyTradeFragment)
                TabLayoutMediator(lyMyTradeList, this) { tab, position ->
                    tab.text = categoryList[position]
                }.attach()
                changeSelectedTabItemFontFamily(0, R.font.notosans_kr_bold)
            }
        }
    }

    override fun initEvent() {
        binding.apply {
            lyMyTradeList.apply {
                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        vpMyTradeList.currentItem = tab?.position!!
                        changeSelectedTabItemFontFamily(tab.position, R.font.notosans_kr_bold)
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                        changeSelectedTabItemFontFamily(tab?.position!!, R.font.notosans_kr_medium)
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {

                    }

                })
            }
        }
    }

    //탭 레이아웃 선택된 폰트 변경
    private fun changeSelectedTabItemFontFamily(tabPosition: Int, @FontRes fontFamilyRes: Int) {
        val linearLayout =
            (binding.lyMyTradeList.getChildAt(0) as ViewGroup).getChildAt(tabPosition) as LinearLayout
        val tabTextView = linearLayout.getChildAt(1) as TextView
        val typeface = ResourcesCompat.getFont(requireContext(), fontFamilyRes)
        tabTextView.typeface = typeface
    }
}