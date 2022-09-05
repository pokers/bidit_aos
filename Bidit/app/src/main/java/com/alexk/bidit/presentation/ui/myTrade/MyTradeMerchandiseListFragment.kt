package com.alexk.bidit.presentation.ui.myTrade

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.alexk.bidit.GlobalApplication
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.common.CommonBidListAdapter
import com.alexk.bidit.common.adapter.common.CommonMerchandiseListAdapter
import com.alexk.bidit.common.util.view.GridRecyclerViewDeco
import com.alexk.bidit.databinding.FragmentCommonMerchandiseListBinding
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.ui.bidding.BiddingActivity
import com.alexk.bidit.presentation.viewModel.BiddingViewModel
import com.alexk.bidit.presentation.viewModel.MerchandiseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MyTradeMerchandiseListFragment :
    BaseFragment<FragmentCommonMerchandiseListBinding>(R.layout.fragment_common_merchandise_list) {

    private val listType by lazy { arguments?.getString("listType") }
    private val merchandiseListAdapter by lazy { CommonMerchandiseListAdapter() }
    private val bidListAdapter by lazy { CommonBidListAdapter() }
    private val merchandiseViewModel by viewModels<MerchandiseViewModel>()
    private val biddingViewModel by viewModels<BiddingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initEvent()
    }

    override fun init() {
        observeMyList()
        binding.rvMerchandiseList.layoutManager = GridLayoutManager(requireContext(),2)
        binding.rvMerchandiseList.addItemDecoration(GridRecyclerViewDeco(12, 12, 0, 37))
        when (listType) {
            "sold" -> {
                merchandiseViewModel.getMyItemList(GlobalApplication.instance.getUserId())
            }
            "purchase" -> {
                biddingViewModel.retrieveBiddingInfo(GlobalApplication.instance.getUserId())
            }
        }
    }

    override fun initEvent() {

    }


    private fun observeMyList() {
        biddingViewModel.biddingInfo.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    loadingDialogShow()
                    Log.d("Bid Loading", "Loading GET my bid list")
                }
                is ViewState.Success -> {
                    loadingDialogDismiss()
                    Log.d("Bid Success", "Success GET my bid list")
                    binding.rvMerchandiseList.adapter = bidListAdapter
                    val result = response.value?.data?.getBidding
                    result?.filterNotNull()
                    if (result?.isNotEmpty() == true) {
                        bidListAdapter.submitList(null)
                        bidListAdapter.submitList(result)
                        bidListAdapter.onItemClicked = {
                            val intent = Intent(requireContext(), BiddingActivity::class.java)
                            intent.putExtra("itemId", it)
                            startActivity(intent)
                        }
                    } else {
                        bidListAdapter.submitList(emptyList())
                        binding.lyNoList.visibility = View.VISIBLE
                    }
                }
                is ViewState.Error -> {
                    loadingDialogDismiss()
                    Log.d("Bid Error", "Error GET my bid list")
                }
            }
        }

        merchandiseViewModel.itemList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    loadingDialogShow()
                    Log.d("My List Loading", "Loading GET my item list")
                }
                is ViewState.Success -> {
                    loadingDialogDismiss()
                    binding.rvMerchandiseList.adapter = merchandiseListAdapter
                    val result = response.value?.data?.getItemList?.edges
                    result?.filterNotNull()
                    if (result?.isNotEmpty() == true) {
                        Log.d("My List Success", "Success GET my item list")
                        merchandiseListAdapter.submitList(null)
                        merchandiseListAdapter.submitList(result)
                        merchandiseListAdapter.onItemClicked =
                            {
                                val intent = Intent(requireContext(), BiddingActivity::class.java)
                                intent.putExtra("itemId", it)
                                startActivity(intent)
                            }
                    } else {
                        Log.d("Empty My List", "Empty GET my item list")
                        merchandiseListAdapter.submitList(emptyList())
                    }
                }
                is ViewState.Error -> {
                    loadingDialogDismiss()
                    Log.d("My List Error", "Error GET my item list")
                }
            }
        }
    }
}