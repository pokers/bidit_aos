package com.alexk.bidit.presentation.ui.category

import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.common.ItemListAdapter
import com.alexk.bidit.common.view.GridRecyclerViewDeco
import com.alexk.bidit.databinding.ActivityCategoryBinding
import com.alexk.bidit.common.util.value.ViewState
import com.alexk.bidit.common.util.setLoadingDialog
import com.alexk.bidit.common.util.value.CATEGORY_ID
import com.alexk.bidit.presentation.ui.category.filter.CategoryFilterDialog
import com.alexk.bidit.presentation.viewModel.ItemViewModel
import com.alexk.bidit.type.CursorType
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.overlay.BalloonOverlayRoundRect
import com.skydoves.balloon.showAlignBottom
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private val itemViewModel by viewModels<ItemViewModel>()
    private val itemListAdapter by lazy { ItemListAdapter() }
    private val itemCursorTypeBalloon by lazy { makeBalloon() }
    private val categoryId by lazy { intent?.getIntExtra(CATEGORY_ID, -1)!! }
    private var currentCursorType = CursorType.createdAt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTitle()
        initItemList()

        addButtonEvent()
        addBalloonEvent(itemCursorTypeBalloon)
        observeCategoryItemList()
    }

    private fun initTitle() {
        binding.apply {
            tvCategoryTitle.text =
                resources.getStringArray(R.array.category_home_item)[categoryId]
            rvMerchandiseList.addItemDecoration(GridRecyclerViewDeco(24, 24, 0, 37))
        }
    }

    private fun initItemList() {
        binding.rvMerchandiseList.apply {
            layoutManager =
                GridLayoutManager(this@CategoryActivity, 2, GridLayoutManager.VERTICAL, false)
            adapter = itemListAdapter
        }
        getItemList()
    }

    private fun getItemList() {
        itemViewModel.getCategoryItemList(categoryId, CursorType.createdAt)
    }

    private fun showCategoryDialog() {
        val dialog =
            CategoryFilterDialog(this@CategoryActivity, categoryId, currentCursorType) {
                itemViewModel.getCategoryFilterItemList(it)
            }
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window?.setBackgroundDrawableResource(R.drawable.bg_filter_dialog)
    }

    private fun addButtonEvent() {
        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }
            btnFilter.setOnClickListener {
                showCategoryDialog()
            }
            lySort.setOnClickListener {
                binding.ivSort.showAlignBottom(itemCursorTypeBalloon)
            }
        }
    }

    private fun makeBalloon(): Balloon {
        return Balloon.Builder(this)
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
    }

    private fun addBalloonEvent(balloon: Balloon) {
        //정렬 방법 선택
        balloon.getContentView().findViewById<RadioButton>(R.id.tv_sort_latest_order).apply {
            setOnCheckedChangeListener { _, isChecked ->
                balloon.dismiss()
                if (isChecked) {
                    binding.tvListSort.text = getString(R.string.category_latest_order)
                    currentCursorType = CursorType.createdAt
                    itemViewModel.getCategoryItemList(categoryId, currentCursorType)
                    typeface =
                        ResourcesCompat.getFont(this@CategoryActivity, R.font.notosans_kr_bold)
                } else {
                    typeface =
                        ResourcesCompat.getFont(this@CategoryActivity, R.font.notosans_kr_medium)
                }
            }
        }
        balloon.getContentView().findViewById<RadioButton>(R.id.tv_sort_deadline_imminent).apply {
            setOnCheckedChangeListener { _, isChecked ->
                balloon.dismiss()
                if (isChecked) {
                    binding.tvListSort.text = getString(R.string.category_deadline_imminent)
                    currentCursorType = CursorType.dueDate
                    itemViewModel.getCategoryItemList(categoryId, currentCursorType)
                    typeface =
                        ResourcesCompat.getFont(this@CategoryActivity, R.font.notosans_kr_bold)
                } else {
                    typeface =
                        ResourcesCompat.getFont(this@CategoryActivity, R.font.notosans_kr_medium)
                }
            }
        }
    }

    private fun setNoItemListLayout() {
        itemListAdapter.submitList(emptyList())
        binding.lyNoList.visibility = View.VISIBLE
    }

    private fun observeCategoryItemList() {
        itemViewModel.itemList.observe(this) { response ->
            when (response) {
                is ViewState.Loading -> {
                    setLoadingDialog(true)
                }
                is ViewState.Success -> {
                    setLoadingDialog(false)
                    val result = response.value
                    if (result?.itemList?.isEmpty() == true) {
                        setNoItemListLayout()
                    } else {
                        itemListAdapter.submitList(result?.itemList)
                    }
                }
                is ViewState.Error -> {
                    setLoadingDialog(false)
                    itemListAdapter.submitList(emptyList())
                }
            }
        }
    }

    companion object {
        private const val TAG = "CategoryActivity..."
    }
}