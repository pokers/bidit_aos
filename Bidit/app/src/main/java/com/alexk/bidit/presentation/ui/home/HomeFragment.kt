package com.alexk.bidit.presentation.ui.home

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.FontRes
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.home.HomeBannerAutoPageAdapter
import com.alexk.bidit.common.adapter.home.HomeCategoryPageAdapter
import com.alexk.bidit.common.adapter.home.category.HomeCategoryListAdapter
import com.alexk.bidit.common.util.GridRecyclerViewDeco
import com.alexk.bidit.databinding.FragmentHomeBinding
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.tempResponse.TempHomeResponse
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

/* 뷰모델 사용 시, 반드시 밑의 어노테이션 주석처리를 풀어주세요. */

//@AndroidEntryPoint
//@ExperimentalCoroutinesApi
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    var endX = 0

    private val bannerList = listOf(
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background
    )

    private val categoryList by lazy { resources.getStringArray(R.array.category_detail_merchandise) }

    private val tempList = listOf<ArrayList<TempHomeResponse>>(
        arrayListOf(
            TempHomeResponse(
                imgUrl = "123",
                merchandiseName = "상품1",
                endingTime = "마감 1일",
                biddingPeopleCount = 1,
                currentPrice = 100000
            ),
            TempHomeResponse(
                imgUrl = "123",
                merchandiseName = "상품1",
                endingTime = "마감 1일",
                biddingPeopleCount = 1,
                currentPrice = 100000
            ),
            TempHomeResponse(
                imgUrl = "123",
                merchandiseName = "상품1",
                endingTime = "마감 1일",
                biddingPeopleCount = 1,
                currentPrice = 100000
            ),
            TempHomeResponse(
                imgUrl = "123",
                merchandiseName = "상품1",
                endingTime = "마감 1일",
                biddingPeopleCount = 1,
                currentPrice = 100000
            ),
            TempHomeResponse(
                imgUrl = "123",
                merchandiseName = "상품1",
                endingTime = "마감 1일",
                biddingPeopleCount = 1,
                currentPrice = 100000
            ),
        ),
        arrayListOf<TempHomeResponse>(
            TempHomeResponse(
                imgUrl = "123",
                merchandiseName = "상품1",
                endingTime = "마감 1일",
                biddingPeopleCount = 1,
                currentPrice = 100000
            ),
        ),
        arrayListOf(
            TempHomeResponse(
                imgUrl = "123",
                merchandiseName = "상품1",
                endingTime = "마감 1일",
                biddingPeopleCount = 1,
                currentPrice = 100000
            ),
        ),
        arrayListOf(
            TempHomeResponse(
                imgUrl = "123",
                merchandiseName = "상품1",
                endingTime = "마감 1일",
                biddingPeopleCount = 1,
                currentPrice = 100000
            ),
        ),
        arrayListOf(
            TempHomeResponse(
                imgUrl = "123",
                merchandiseName = "상품1",
                endingTime = "마감 1일",
                biddingPeopleCount = 1,
                currentPrice = 100000
            ),
        )
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
                    R.drawable.ic_category_apple,
                    R.drawable.ic_category_galaxy,
                    R.drawable.ic_category_another_phone,
                    R.drawable.ic_category_smart_watch,
                    R.drawable.ic_category_labtop,
                    R.drawable.ic_category_drone,
                    R.drawable.ic_category_tablet,
                    R.drawable.ic_category_monitor,
                    R.drawable.ic_category_game,
                    R.drawable.ic_category_audio,
                    R.drawable.ic_category_camera,
                    R.drawable.ic_category_another_category
                )
            )
            rvCategory.addItemDecoration(GridRecyclerViewDeco(0, 80, 40, 0))

            rvCategory.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }

                @RequiresApi(Build.VERSION_CODES.N)
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    //리사이클러뷰 전체 길이
                    val range = rvCategory.computeHorizontalScrollRange()
                    //dpi
                    val density = resources.displayMetrics.density

                    //최대 거리(thumb /2)
                    val maxEndX = range - resources.displayMetrics.widthPixels + (20 * density) + 12

                    //슬라이딩 거리
                    endX += dx

                    val proportion = endX.div(maxEndX)
                    val transMaxRange = lyCategoryScrollBar.width - viewSlipFront.width
                    viewSlipFront.translationX = transMaxRange * proportion
                }
            })

            vpMainBanner.adapter = HomeBannerAutoPageAdapter(context, bannerList)
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
            changeSelectedTabItemFontFamily(0, R.font.notosans_kr_bold)

            lyDetailCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    vpMerchandiseList.currentItem = tab?.position!!
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

    private fun changeSelectedTabItemFontFamily(tabPosition: Int, @FontRes fontFamilyRes: Int) {
        val linearLayout =
            (binding.lyDetailCategory.getChildAt(0) as ViewGroup).getChildAt(tabPosition) as LinearLayout
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