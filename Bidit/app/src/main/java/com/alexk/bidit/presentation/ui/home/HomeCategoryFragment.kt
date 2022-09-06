package com.alexk.bidit.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.GetItemListQuery
import com.alexk.bidit.common.adapter.common.CommonMerchandiseListAdapter
import com.alexk.bidit.common.util.view.GridRecyclerViewDeco
import com.alexk.bidit.databinding.FragmentCommonMerchandiseListBinding
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.common.dialog.LoadingDialog
import com.alexk.bidit.presentation.ui.bidding.BiddingActivity
import com.alexk.bidit.presentation.viewModel.ItemViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class HomeCategoryFragment :
    Fragment() {

    private var _binding: FragmentCommonMerchandiseListBinding? = null
    private val binding get() = _binding!!

    private val merchandiseAdapter by lazy { CommonMerchandiseListAdapter() }
    private val viewModel by viewModels<ItemViewModel>()
    private val sortType by lazy { arguments?.getString("sortType") }
    private val loadingDialog by lazy { LoadingDialog(requireContext()) }
    private val merchandiseList = mutableListOf<GetItemListQuery.Edge>()

    private var firstInfo = 0
    private var lastInfo = 10
    private var nextPage : Boolean? = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommonMerchandiseListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerview()
        initItemViewModel()
    }

    private fun initRecyclerview() {
        binding.rvMerchandiseList.apply {
            //첫 데이터 통신은 뭐든 가져와야함
            viewModel.getSortTypeItemList(firstInfo = firstInfo, lastInfo = lastInfo, sortType = sortType!!)
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = merchandiseAdapter
            addItemDecoration(GridRecyclerViewDeco(12, 12, 0, 37))
            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!canScrollVertically(1)) {
                        //스크롤이 최대로 내려간 상태
                        retrieveItemList()
                    }
                }
            })
        }
    }

    private fun retrieveItemList() {
        binding.rvMerchandiseList.apply {
            //다음 페이지가 있을때만 통신
            if(nextPage == true){
                viewModel.getSortTypeItemList(firstInfo = firstInfo, lastInfo = lastInfo, sortType = sortType!!)
            }
        }
    }

    private fun initItemViewModel() {
        //fragment는 viewLifeCycleOwner로
        viewModel.itemList.observe(viewLifecycleOwner) { response ->
            when (response) {
                //서버 연결 대기중
                is ViewState.Loading -> {
                    loadingDialog.show()
                    Log.d(TAG, "Loading GET merchandise list")
                }
                //아이템 가져오기 성공
                is ViewState.Success -> {
                    loadingDialog.dismiss()
                    Log.d(TAG, "Success GET merchandise list")
                    val result = response.value?.data?.getItemList?.edges
                    if (result?.size == 0 || result == null) {
                        Log.d(TAG, "No merchandise data")
                        nextPage = false
                    } else {
                        //다음페이지가 존재
                        nextPage = response.value.data?.getItemList?.pageInfo?.hasNextPage
                        if(nextPage == true){
                            lastInfo += 10
                            firstInfo += 10
                        }
                        merchandiseAdapter.onItemClicked =
                            {
                                val intent = Intent(requireContext(), BiddingActivity::class.java)
                                intent.putExtra("itemId", it)
                                startActivity(intent)
                            }
                        for (idx in result.indices) {
                            if (result[idx]?.node?.status == 1 || result[idx]?.node?.status == 0)
                                merchandiseList.add(result[idx]!!)
                        }
                        merchandiseAdapter.submitList(merchandiseList)
                    }
                }
                //서버 연결 실패(만료) -> 재발급 요청
                is ViewState.Error -> {
                    loadingDialog.dismiss()
                    merchandiseAdapter.submitList(emptyList())
                    Log.d(TAG, "Fail GET merchandise list")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "HomeCategoryFragment..."
    }
}