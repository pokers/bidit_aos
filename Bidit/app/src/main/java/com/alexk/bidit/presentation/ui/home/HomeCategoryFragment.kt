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
import com.alexk.bidit.common.adapter.common.CommonMerchandiseListAdapter
import com.alexk.bidit.common.util.view.GridRecyclerViewDeco
import com.alexk.bidit.databinding.FragmentCommonMerchandiseListBinding
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.common.dialog.LoadingDialog
import com.alexk.bidit.presentation.ui.bidding.BiddingActivity
import com.alexk.bidit.presentation.viewModel.MerchandiseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class HomeCategoryFragment :
    Fragment() {

    private var _binding: FragmentCommonMerchandiseListBinding? = null
    private val binding get() = _binding!!

    private val merchandiseAdapter by lazy { CommonMerchandiseListAdapter() }
    private val viewModel by viewModels<MerchandiseViewModel>()

    private val sortType by lazy { arguments?.getString("sortType") }

    private val loadingDialog by lazy { LoadingDialog(requireContext()) }

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
        binding.apply {
            rvMerchandiseList.layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            rvMerchandiseList.adapter = merchandiseAdapter
            rvMerchandiseList.addItemDecoration(GridRecyclerViewDeco(12, 12, 0, 37))
        }
        observeMerchandiseList()
    }

    private fun observeMerchandiseList() {
        //fragment는 viewLifeCycleOwner로
        viewModel.itemList.observe(viewLifecycleOwner) { response ->
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
                        Log.d("Empty Merchandise List","No merchandise data")
                        merchandiseAdapter.submitList(emptyList())
                    } else {
                        merchandiseAdapter.onItemClicked =
                            {
                                val intent = Intent(requireContext(), BiddingActivity::class.java)
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

    override fun onResume() {
        super.onResume()
        viewModel.getSortTypeItemList(sortType!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}