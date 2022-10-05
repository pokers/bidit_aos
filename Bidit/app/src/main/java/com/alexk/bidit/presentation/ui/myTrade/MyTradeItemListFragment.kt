package com.alexk.bidit.presentation.ui.myTrade

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.common.CommonItemListAdapter
import com.alexk.bidit.common.util.setLoadingDialog
import com.alexk.bidit.common.util.typeCastMyBiddingItemToItemEntity
import com.alexk.bidit.common.util.view.GridRecyclerViewDeco
import com.alexk.bidit.databinding.FragmentCommonMerchandiseListBinding
import com.alexk.bidit.common.util.view.ViewState
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

    private fun init() {
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

    private fun initEvent() {

    }


    //view model을 초기화하는건데 왜 다른것 까지 해야하나?
    //메소드를 만들어서 따로 관리를 해주자 -> 네이밍도 신경써주기
    private fun initUserViewModel() {
        userViewModel.myInfo.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    context?.setLoadingDialog(true)
                    Log.d(TAG, "Loading my sold list")
                }
                is ViewState.Success -> {
                    context?.setLoadingDialog(false)
                    val result = response.value?.itemConnection
                    if(result?.itemList?.isEmpty() == true){
                        binding.lyNoList.visibility = View.VISIBLE
                    }
                    itemListAdapter.submitList(emptyList())
                    itemListAdapter.onItemClicked = {
                        val intent = Intent(requireContext(), BiddingActivity::class.java)
                        intent.putExtra("itemId", it)
                        startActivity(intent)
                    }
                }
                is ViewState.Error -> {
                    context?.setLoadingDialog(false)
                    Log.e(TAG, "Error my sold list")
                }
            }
        }
    }

    private fun initBiddingViewModel() {
        biddingViewModel.myBiddingInfo.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    context?.setLoadingDialog(false)
                    Log.d(TAG, "Loading GET my bid list")
                }
                is ViewState.Success -> {
                    context?.setLoadingDialog(false)
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
                    context?.setLoadingDialog(false)
                    Log.d(TAG, "Error GET my bid list")
                }
            }
        }
    }

    companion object {
        private const val TAG = "MyTradeMerchandiseListFragment..."
    }
}