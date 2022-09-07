package com.alexk.bidit.presentation.ui.myTrade

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.alexk.bidit.GlobalApplication
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.common.CommonItemListAdapter
import com.alexk.bidit.common.util.typeCastBiddingItemToItemEntity
import com.alexk.bidit.common.util.typeCastMyBiddingItemToItemEntity
import com.alexk.bidit.common.util.typeCastUsersItemToItemEntity
import com.alexk.bidit.common.util.view.GridRecyclerViewDeco
import com.alexk.bidit.databinding.FragmentCommonMerchandiseListBinding
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.ui.item.BiddingActivity
import com.alexk.bidit.presentation.viewModel.BiddingViewModel
import com.alexk.bidit.presentation.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MyTradeItemListFragment :
    BaseFragment<FragmentCommonMerchandiseListBinding>(R.layout.fragment_common_merchandise_list) {

    private val listType by lazy { arguments?.getString("listType") }

    private val itemListAdapter by lazy { CommonItemListAdapter() }

    private val userViewModel by viewModels<UserViewModel>()
    private val biddingViewModel by viewModels<BiddingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initEvent()
    }

    override fun init() {
        initBiddingViewModel()
        initUserViewModel()
        binding.rvMerchandiseList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMerchandiseList.adapter = itemListAdapter
        binding.rvMerchandiseList.addItemDecoration(GridRecyclerViewDeco(12, 12, 0, 37))
        when (listType) {
            "sold" -> {
                userViewModel.getMyInfo()
            }
            "bid" -> {
                biddingViewModel.getMyBiddingInfo()
            }
        }
    }

    override fun initEvent() {

    }


    private fun initUserViewModel() {
        userViewModel.myInfo.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    loadingDialogShow()
                    Log.d(TAG, "Loading my sold list")
                }
                is ViewState.Success -> {
                    loadingDialogDismiss()
                    val result = response.value?.data?.me?.items?.edges
                    if(result?.isEmpty() == true){
                        binding.lyNoList.visibility = View.VISIBLE
                    }
                    itemListAdapter.submitList(typeCastUsersItemToItemEntity(result))
                    itemListAdapter.onItemClicked = {
                        val intent = Intent(requireContext(), BiddingActivity::class.java)
                        intent.putExtra("itemId", it)
                        startActivity(intent)
                    }
                }
                is ViewState.Error -> {
                    loadingDialogDismiss()
                    Log.e(TAG, "Error my sold list")
                }
            }
        }
    }

    private fun initBiddingViewModel() {
        biddingViewModel.myBiddingInfo.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    loadingDialogShow()
                    Log.d(TAG, "Loading GET my bid list")
                }
                is ViewState.Success -> {
                    loadingDialogDismiss()
                    Log.d(TAG, "Success GET my bid list")
                    val result = response.value?.data?.getMyBidding
                    if(result?.isEmpty() == true){
                        binding.lyNoList.visibility = View.VISIBLE
                    }
                    itemListAdapter.submitList(typeCastMyBiddingItemToItemEntity(result))

                    itemListAdapter.onItemClicked = {
                        val intent = Intent(requireContext(), BiddingActivity::class.java)
                        intent.putExtra("itemId", it)
                        startActivity(intent)
                    }
                }
                is ViewState.Error -> {
                    loadingDialogDismiss()
                    Log.d(TAG, "Error GET my bid list")
                }
            }
        }
    }

    companion object {
        private const val TAG = "MyTradeMerchandiseListFragment..."
    }
}