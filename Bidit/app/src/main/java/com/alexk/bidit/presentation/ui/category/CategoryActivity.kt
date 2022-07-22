package com.alexk.bidit.presentation.ui.category

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.common.CommonMerchandiseListAdapter
import com.alexk.bidit.common.util.view.GridRecyclerViewDeco
import com.alexk.bidit.databinding.ActivityCategoryBinding
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.dialog.LoadingDialog
import com.alexk.bidit.presentation.ui.bidding.BiddingActivity
import com.alexk.bidit.presentation.viewModel.MerchandiseViewModel
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.overlay.BalloonOverlayRoundRect
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private var currentSortType = "latestOrder"
    private val viewModel by viewModels<MerchandiseViewModel>()
    private val merchandiseAdapter by lazy { CommonMerchandiseListAdapter() }
    private val categoryId by lazy { intent?.getIntExtra("categoryId", 0)?.plus(2) }
    private val loadingDialog by lazy { LoadingDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initEvent()
    }

    private fun init() {
        binding.apply {
            tvCategoryTitle.text =
                resources.getStringArray(R.array.category_home_item)[categoryId!! - 2]
            rvMerchandiseList.layoutManager =
                GridLayoutManager(this@CategoryActivity, 2, GridLayoutManager.VERTICAL, false)
            rvMerchandiseList.adapter = merchandiseAdapter
            rvMerchandiseList.addItemDecoration(GridRecyclerViewDeco(12, 12, 0, 37))
        }
        Log.d("categoryId", categoryId.toString())
        viewModel.getCategoryItemList(categoryId!!, "latestOrder")
        observeCategoryItemList()
    }

    private fun initEvent() {
        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }
            btnFilter.setOnClickListener {
                val dialog =
                    CategoryFilterDialog(this@CategoryActivity) {
                        //필터데이터로 업데이트
                    }
                dialog.setCanceledOnTouchOutside(true)
                dialog.show()
                dialog.window?.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
                )
                dialog.window?.setBackgroundDrawableResource(R.drawable.bg_filter_dialog)
                dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
            }
            lySort.setOnClickListener {
                makeBalloon(currentSortType).showAlignBottom(binding.ivSort)
            }
        }
    }

    private fun makeBalloon(sortType: String): Balloon {
        val balloon = Balloon.Builder(this)
            .setLayout(R.layout.dialog_merchandise_sort_type)
            .setArrowSize(10)
            .setArrowOrientation(ArrowOrientation.TOP)
            .setArrowPosition(0.5f)
            .setMarginTop(12)
            .setElevation(0)
            .setIsVisibleOverlay(true)
            .setDismissWhenOverlayClicked(false)
            .setOverlayColor(ContextCompat.getColor(this, R.color.black_trans_40))
            .setCornerRadius(4f)
            .setOverlayPosition(Point(-1000, -1000))
            .setOverlayShape(BalloonOverlayRoundRect(0f, 0f))
            .setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            .setLifecycleOwner(this)
            .build()

        //정렬 방법 선택
        val latestTextView =
            balloon.getContentView().findViewById<TextView>(R.id.tv_sort_latest_order).apply {
                setOnClickListener {
                    currentSortType = "latestOrder"
                    balloon.dismiss()
                    binding.tvListSort.text = getString(R.string.category_latest_order)
                    viewModel.getCategoryItemList(categoryId!!, currentSortType)
                }
            }

        val deadlineTextView =
            balloon.getContentView().findViewById<TextView>(R.id.tv_sort_deadline_imminent).apply {
                setOnClickListener {
                    currentSortType = "deadline"
                    balloon.dismiss()
                    binding.tvListSort.text = getString(R.string.category_deadline_imminent)
                    viewModel.getCategoryItemList(categoryId!!, currentSortType)
                }
            }

        val popularTextView =
            balloon.getContentView().findViewById<TextView>(R.id.tv_sort_popular).apply {
                setOnClickListener {
                    currentSortType = "popular"
                    balloon.dismiss()
                    binding.tvListSort.text = getString(R.string.category_popular)
                    viewModel.getCategoryItemList(categoryId!!, currentSortType)
                }
            }

        //sortType에 따른 글자 변화
        when (sortType) {
            "latestOrder" -> {
                latestTextView.typeface =
                    ResourcesCompat.getFont(this@CategoryActivity, R.font.notosans_kr_bold)
                latestTextView.setTextColor(ResourcesCompat.getColor(resources, R.color.nero, null))
            }
            "deadline" -> {
                deadlineTextView.typeface =
                    ResourcesCompat.getFont(this@CategoryActivity, R.font.notosans_kr_bold)
                deadlineTextView.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.nero,
                        null
                    )
                )
            }
            "popular" -> {
                popularTextView.typeface =
                    ResourcesCompat.getFont(this@CategoryActivity, R.font.notosans_kr_bold)
                popularTextView.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.nero,
                        null
                    )
                )
            }
        }

        return balloon
    }

    private fun observeCategoryItemList() {
        //fragment는 viewLifeCycleOwner로
        viewModel.itemList.observe(this) { response ->
            when (response) {
                //서버 연결 대기중
                is ViewState.Loading -> {
                    loadingDialog.show()
                    Log.d("Merchandise Loading", "Loading GET merchandise list")
                }
                //아이템 가져오기 성공
                is ViewState.Success -> {
                    loadingDialog.dismiss()
                    Log.d("Merchandise Success", "Success GET merchandise list")
                    //리사이클러뷰 어댑터 연결
                    val result = response.value?.data?.getItemList?.edges
                    if (result?.size == 0) {
                        merchandiseAdapter.submitList(emptyList())
                        binding.lyNoList.visibility = View.VISIBLE
                    } else {
                        merchandiseAdapter.onItemClicked = {
                            val intent = Intent(this, BiddingActivity::class.java)
                            intent.putExtra("itemId", it)
                            startActivity(intent)
                        }
                        merchandiseAdapter.submitList(result)
                    }
                }
                //서버 연결 실패(만료) -> 재발급 요청
                is ViewState.Error -> {
                    loadingDialog.dismiss()
                    merchandiseAdapter.submitList(emptyList())
                    Log.d("Merchandise Failure", "Fail GET merchandise list")
                }
            }
        }
    }
}