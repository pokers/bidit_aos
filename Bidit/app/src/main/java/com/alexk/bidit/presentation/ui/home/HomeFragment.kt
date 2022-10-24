package com.alexk.bidit.presentation.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.FontRes
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.home.HomeCategoryPageAdapter
import com.alexk.bidit.common.adapter.home.banner.HomeBannerFragmentAdapter
import com.alexk.bidit.common.adapter.home.category.HomeCategoryListAdapter
import com.alexk.bidit.common.view.GridRecyclerViewDeco
import com.alexk.bidit.databinding.FragmentHomeBinding
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.ui.selling.SellingActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private var categoryScrollBarEndX = 0
    private val categoryList by lazy { resources.getStringArray(R.array.category_detail_merchandise) }
    private val categoryImg = listOf(
        R.drawable.ic_category_apple,
        R.drawable.ic_category_galaxy,
        R.drawable.ic_category_another_phone,
        R.drawable.ic_category_smart_watch,
        R.drawable.ic_category_labtop,
        R.drawable.ic_category_tablet,
        R.drawable.ic_category_monitor,
        R.drawable.ic_category_game,
        R.drawable.ic_category_audio,
        R.drawable.ic_category_camera,
        R.drawable.ic_category_drone,
        R.drawable.ic_category_another_category
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initItemCategoryList()
        initBannerList()
        initItemListPage()

        addItemButtonEvent()
        addItemListPageSelectedEvent()
    }

    private fun initItemCategoryList() {
        binding.rvCategory.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false)
            adapter = HomeCategoryListAdapter(
                requireContext(), categoryImg
            )
            addItemDecoration(GridRecyclerViewDeco(0, 80, 40, 0))
        }
        addItemCategoryScrollBar(isRecyclerScrollable(binding.rvCategory))
    }

    private fun isRecyclerScrollable(recyclerView : RecyclerView): Boolean {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val adapter = recyclerView.adapter
        return if (adapter == null) false else layoutManager.findLastCompletelyVisibleItemPosition() < adapter.itemCount - 1
    }

    private fun addItemCategoryScrollBar(isRecyclerScrollable: Boolean) {
        binding.apply {
            if(isRecyclerScrollable){
                rvCategory.apply {
                    addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        @RequiresApi(Build.VERSION_CODES.N)
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            //리사이클러뷰 전체 길이
                            val range = computeHorizontalScrollRange()
                            //dpi
                            val density = resources.displayMetrics.density
                            //최대 거리(thumb /2)
                            val maxEndX =
                                range - resources.displayMetrics.widthPixels + (20 * density) + 12

                            //슬라이딩 거리
                            categoryScrollBarEndX += dx

                            val proportion = categoryScrollBarEndX.div(maxEndX)
                            val transMaxRange =
                                binding.lyCategoryScrollBar.width - binding.viewSlipFront.width
                            binding.viewSlipFront.translationX = transMaxRange * proportion
                        }
                    })
                }
            }else{
                lyCategoryScrollBar.visibility = View.GONE
            }
        }
    }

    private fun initBannerList() {
        binding.apply {
            vpMainBanner.adapter = HomeBannerFragmentAdapter(this@HomeFragment)
        }
        initBannerIndicator()
    }

    private fun initBannerIndicator() {
        binding.ciMainBanner.apply {
            setViewPager(binding.vpMainBanner)
        }
    }

    private fun initItemListPage() {
        binding.vpMerchandiseList.apply {
            adapter = HomeCategoryPageAdapter(this@HomeFragment)
            connectItemListPageToCategoryTab()
            changeSelectedTabTextFontFamily(0, R.font.notosans_kr_bold)
        }
    }

    private fun connectItemListPageToCategoryTab() {
        TabLayoutMediator(binding.lyDetailCategory, binding.vpMerchandiseList) { tab, position ->
            tab.text = categoryList[position]
        }.attach()
    }

    private fun addItemButtonEvent() {
        binding.btnAddMerchandise.setOnClickListener {
            startActivity(Intent(requireContext(), SellingActivity::class.java))
        }
    }

    private fun addItemListPageSelectedEvent() {
        binding.lyDetailCategory.apply {
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    binding.vpMerchandiseList.currentItem = tab?.position!!
                    changeSelectedTabTextFontFamily(tab.position, R.font.notosans_kr_bold)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    changeSelectedTabTextFontFamily(tab?.position!!, R.font.notosans_kr_medium)
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })
        }
    }

    //탭 레이아웃 선택된 폰트 변경
    private fun changeSelectedTabTextFontFamily(tabPosition: Int, @FontRes fontFamilyRes: Int) {
        val linearLayout =
            (binding.lyDetailCategory.getChildAt(0) as ViewGroup).getChildAt(tabPosition) as LinearLayout
        val tabTextView = linearLayout.getChildAt(1) as TextView
        val typeface = ResourcesCompat.getFont(requireContext(), fontFamilyRes)
        tabTextView.typeface = typeface
    }

    companion object{
        private const val TAG = "HomeFragment..."
    }
}