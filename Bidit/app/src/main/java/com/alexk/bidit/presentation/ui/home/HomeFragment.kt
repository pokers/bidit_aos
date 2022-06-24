package com.alexk.bidit.presentation.ui.home

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.home.HomeBannerPageAdapter
import com.alexk.bidit.common.adapter.home.HomeCategoryPageAdapter
import com.alexk.bidit.common.adapter.home.category.HomeCategoryListAdapter
import com.alexk.bidit.common.util.GridRecyclerViewDeco
import com.alexk.bidit.data.service.response.home.HomeCategoryResponse
import com.alexk.bidit.data.service.response.home.HomeResponse
import com.alexk.bidit.databinding.FragmentHomeBinding
import com.alexk.bidit.presentation.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

//@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val bannerList = listOf(
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background
    )

    private val categoryList by lazy { resources.getStringArray(R.array.category_detail_merchandise) }

    private val tempList = listOf(
        arrayListOf(
            HomeResponse(img = "123", name = "상품1", time = "마감 1일", price = 100000),
            HomeResponse(img = "123", name = "상품1", time = "마감 1일", price = 100000),
            HomeResponse(img = "123", name = "상품1", time = "마감 1일", price = 100000),
            HomeResponse(img = "123", name = "상품1", time = "마감 1일", price = 100000),
            HomeResponse(img = "123", name = "상품1", time = "마감 1일", price = 100000),
            HomeResponse(img = "123", name = "상품1", time = "마감 1일", price = 100000),
        ),
        arrayListOf(HomeResponse(img = "123", name = "상품2", time = "마감 1일", price = 100000)),
        arrayListOf(HomeResponse(img = "123", name = "상품3", time = "마감 1일", price = 100000)),
        arrayListOf(HomeResponse(img = "123", name = "상품4", time = "마감 1일", price = 100000)),
        arrayListOf(HomeResponse(img = "123", name = "상품5", time = "마감 1일", price = 100000))
    )

    lateinit var slideJob: Job
    private var bannerPosition = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initEvent()
    }

    private fun slideJobCreate() {
        slideJob = lifecycleScope.launchWhenResumed {
            delay(2000)
            binding.vpMainBanner.setCurrentItem(bannerPosition++, true)
        }
    }

    override fun init() {

        binding.apply {

            rvCategory.layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false)
            rvCategory.adapter = HomeCategoryListAdapter(
                requireContext(), listOf(
                    HomeCategoryResponse("test", "test"),
                    HomeCategoryResponse("test", "test"),
                    HomeCategoryResponse("test", "test"),
                    HomeCategoryResponse("test", "test"),
                    HomeCategoryResponse("test", "test"),
                    HomeCategoryResponse("test", "test"),
                    HomeCategoryResponse("test", "test"),
                    HomeCategoryResponse("test", "test"),
                )
            )
            rvCategory.addItemDecoration(GridRecyclerViewDeco(0,40,20,0))

            vpMainBanner.adapter = HomeBannerPageAdapter(context, bannerList)
            vpMainBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    when (state) {
                        ViewPager2.SCROLL_STATE_SETTLING -> {

                        }
                        //멈춤
                        ViewPager2.SCROLL_STATE_IDLE -> {
                            if (!slideJob.isActive) slideJobCreate()
                        }
                        //드래그
                        ViewPager2.SCROLL_STATE_DRAGGING -> {
                            slideJob.cancel()
                        }
                    }
                }
            })

            //ciMainBanner.setViewPager(vpMainBanner)
            vpMerchandiseList.adapter = HomeCategoryPageAdapter(this@HomeFragment, tempList)

            TabLayoutMediator(lyDetailCategory, vpMerchandiseList) { tab, position ->
                tab.text = categoryList[position]
            }.attach()


            //default로 0일 때 bold처리
            changeSelectedTabItemFontFamily(0,R.font.notosans_kr_bold)

            lyDetailCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    vpMerchandiseList.currentItem = tab?.position!!
                    changeSelectedTabItemFontFamily(tab.position,R.font.notosans_kr_bold)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    changeSelectedTabItemFontFamily(tab?.position!!,R.font.notosans_kr_medium)
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            })
        }
    }

    private fun changeSelectedTabItemFontFamily(tabPosition: Int, @FontRes fontFamilyRes: Int) {
        val linearLayout = (binding.lyDetailCategory.getChildAt(0) as ViewGroup).getChildAt(tabPosition) as LinearLayout
        val tabTextView = linearLayout.getChildAt(1) as TextView
        val typeface = ResourcesCompat.getFont(requireContext(), fontFamilyRes)
        tabTextView.typeface = typeface
    }

    override fun initEvent() {

    }

    override fun onPause() {
        super.onPause()
        slideJob.cancel()
    }

    override fun onResume() {
        super.onResume()
        slideJobCreate()
    }
}