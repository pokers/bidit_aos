package com.alexk.bidit.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.home.HomeBannerAdapter
import com.alexk.bidit.common.adapter.home.HomeCategoryAdapter
import com.alexk.bidit.data.service.response.home.HomeResponse
import com.alexk.bidit.databinding.FragmentHomeBinding
import com.alexk.bidit.presentation.base.BaseFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

//@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val bannerList = listOf(
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background
    )

    private val tempList = listOf(
        arrayListOf(HomeResponse(img = "123", name = "상품1", time = "마감 1일", price = 100000)),
        arrayListOf(HomeResponse(img = "123", name = "상품2", time = "마감 1일", price = 100000)),
        arrayListOf(HomeResponse(img = "123", name = "상품3", time = "마감 1일", price = 100000)),
        arrayListOf(HomeResponse(img = "123", name = "상품4", time = "마감 1일", price = 100000)),
        arrayListOf(HomeResponse(img = "123", name = "상품5", time = "마감 1일", price = 100000)),
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
            //카테고리 초기화
            itemCategoryEndingSoon.apply {
                setCategoryName(context.getString(R.string.category_ending_soon))
                setFirstSelectItem()
                setOnClickListener {
                    clickCategory(
                        itemCategoryLatestOrder,
                        itemCategoryPopular,
                        itemCategoryPromotion,
                        itemCategoryTemp,
                        this
                    )
                    vpMerchandiseList.currentItem = 0
                }
            }
            itemCategoryLatestOrder.apply {
                setCategoryName(context.getString(R.string.category_latest_order))
                setOnClickListener {
                    clickCategory(
                        itemCategoryEndingSoon,
                        itemCategoryPopular,
                        itemCategoryPromotion,
                        itemCategoryTemp,
                        this
                    )
                }
                vpMerchandiseList.currentItem = 1
            }
            itemCategoryPromotion.apply {
                setCategoryName(context.getString(R.string.category_promotion))
                setOnClickListener {
                    clickCategory(
                        itemCategoryLatestOrder,
                        itemCategoryPopular,
                        itemCategoryEndingSoon,
                        itemCategoryTemp,
                        this
                    )
                    vpMerchandiseList.currentItem = 2
                }
            }
            itemCategoryPopular.apply {
                setCategoryName(context.getString(R.string.category_popular_item))
                setOnClickListener {
                    clickCategory(
                        itemCategoryLatestOrder,
                        itemCategoryEndingSoon,
                        itemCategoryPromotion,
                        itemCategoryTemp,
                        this
                    )
                    vpMerchandiseList.currentItem = 3
                }
            }
            itemCategoryTemp.apply {
                setCategoryName(context.getString(R.string.category_popular_item))
                setOnClickListener {
                    clickCategory(
                        itemCategoryLatestOrder,
                        itemCategoryPopular,
                        itemCategoryPromotion,
                        itemCategoryEndingSoon,
                        this
                    )
                    vpMerchandiseList.currentItem = 4
                }
            }

            vpMainBanner.adapter = HomeBannerAdapter(context, bannerList)
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

            //indicator 메모리 문제
            //ciMainBanner.setViewPager(vpMainBanner)

            vpMerchandiseList.adapter = HomeCategoryAdapter(this@HomeFragment, tempList)
            vpMerchandiseList.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> {
                            itemCategoryEndingSoon.clickCategory(
                                itemCategoryLatestOrder,
                                itemCategoryPopular,
                                itemCategoryPromotion,
                                itemCategoryTemp,
                                itemCategoryEndingSoon
                            )
                        }
                        1 -> {
                            itemCategoryLatestOrder.clickCategory(
                                itemCategoryEndingSoon,
                                itemCategoryPopular,
                                itemCategoryPromotion,
                                itemCategoryTemp,
                                itemCategoryLatestOrder
                            )
                        }
                        2 -> {
                            itemCategoryPromotion.clickCategory(
                                itemCategoryLatestOrder,
                                itemCategoryPopular,
                                itemCategoryEndingSoon,
                                itemCategoryTemp,
                                itemCategoryPromotion
                            )
                        }
                        3 -> {
                            itemCategoryPopular.clickCategory(
                                itemCategoryLatestOrder,
                                itemCategoryEndingSoon,
                                itemCategoryPromotion,
                                itemCategoryTemp,
                                itemCategoryPopular
                            )
                        }
                        4 -> {
                            itemCategoryTemp.clickCategory(
                                itemCategoryLatestOrder,
                                itemCategoryPopular,
                                itemCategoryPromotion,
                                itemCategoryEndingSoon,
                                itemCategoryTemp
                            )
                        }
                    }
                }
            })
        }
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