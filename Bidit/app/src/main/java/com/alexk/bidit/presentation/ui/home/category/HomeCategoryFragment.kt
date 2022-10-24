package com.alexk.bidit.presentation.ui.home.category

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.common.ItemListAdapter
import com.alexk.bidit.common.view.GridRecyclerViewDeco
import com.alexk.bidit.databinding.FragmentCommonMerchandiseListBinding
import com.alexk.bidit.common.view.ViewState
import com.alexk.bidit.common.util.setLoadingDialog
import com.alexk.bidit.common.util.value.ITEM_ID
import com.alexk.bidit.common.util.value.ITEM_CATEGORY_TYPE
import com.alexk.bidit.domain.entity.item.ItemBasicEntity
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.ui.bid.BiddingActivity
import com.alexk.bidit.presentation.viewModel.ItemViewModel
import com.alexk.bidit.type.CursorType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class HomeCategoryFragment :
    BaseFragment<FragmentCommonMerchandiseListBinding>(R.layout.fragment_common_merchandise_list) {

    private val itemListAdapter by lazy { ItemListAdapter() }
    private val itemViewModel by viewModels<ItemViewModel>()
    private lateinit var sortType: CursorType

    private var nextFirstItemCount = 0
    private var nextLastItemCount = 10
    private var hasNextPage: Boolean = true
    private var existItemData : Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sortType = initSortType()

        initItemList()
        observeItemList()
    }

    private fun initSortType(): CursorType {
        return when (arguments?.getString(ITEM_CATEGORY_TYPE)) {
            "DEADLINE" -> {
                CursorType.dueDate
            }
            else -> {
                CursorType.createdAt
            }
        }
    }

    private fun initItemList() {
        itemViewModel.getSortTypeItemList(
            firstInfo = nextFirstItemCount,
            lastInfo = nextLastItemCount,
            cursorType = sortType
        )

        binding.rvMerchandiseList.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = itemListAdapter
            addItemDecoration(GridRecyclerViewDeco(12, 12, 0, 37))

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!canScrollVertically(1)) {
                        //스크롤이 최대로 내려간 상태
                        getNextItemList()
                    }
                }
            })
        }
    }

    private fun getNextItemList() {
        if (hasNextPage) {
            itemViewModel.getSortTypeItemList(
                firstInfo = nextFirstItemCount,
                lastInfo = nextLastItemCount,
                cursorType = sortType
            )
        }
    }

    private fun setNextPageCount() {
        nextLastItemCount += GET_ITEM_COUNT
        nextFirstItemCount += GET_ITEM_COUNT
    }

    private fun addItemClickEvent() {
        itemListAdapter.onItemClicked = {
            val intent = Intent(requireContext(), BiddingActivity::class.java)
            intent.putExtra(ITEM_ID, it)
            startActivity(intent)
        }
    }

    private fun setNoItemListLayout() {
        if(!existItemData){
            itemListAdapter.submitList(emptyList())
            binding.lyNoList.visibility = View.VISIBLE
        }
    }

    private fun addItemToList(nextPageAvailable: Boolean, itemList: List<ItemBasicEntity>) {
        binding.lyNoList.visibility = View.GONE
        existItemData = true
        hasNextPage = nextPageAvailable
        if (nextPageAvailable) {
            setNextPageCount()
        }
        addItemClickEvent()
        itemListAdapter.submitList(itemList)
    }

    private fun observeItemList() {
        itemViewModel.itemList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    context?.setLoadingDialog(true)
                }
                is ViewState.Success -> {
                    context?.setLoadingDialog(false)
                    val result = response.value
                    if (result?.itemList?.isEmpty() == true) {
                        setNoItemListLayout()
                    } else {
                        addItemToList(result?.itemPageInfo?.hasNextPage!!, result.itemList!!)
                    }
                }
                is ViewState.Error -> {
                    context?.setLoadingDialog(false)
                    itemListAdapter.submitList(emptyList())
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        itemViewModel.getSortTypeItemList(
            firstInfo = nextFirstItemCount,
            lastInfo = nextLastItemCount,
            cursorType = sortType
        )
    }

    companion object {
        private const val GET_ITEM_COUNT = 10
    }
}