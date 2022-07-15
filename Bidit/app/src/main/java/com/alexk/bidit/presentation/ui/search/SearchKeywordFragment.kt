package com.alexk.bidit.presentation.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.common.CommonMerchandiseListAdapter
import com.alexk.bidit.common.adapter.search.SearchKeywordListAdapter
import com.alexk.bidit.common.util.GridRecyclerViewDeco
import com.alexk.bidit.data.sharedPreference.SearchKeywordManager
import com.alexk.bidit.databinding.FragmentSearchKeywordBinding
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.ui.bidding.BiddingActivity
import com.alexk.bidit.presentation.viewModel.MerchandiseViewModel
import com.alexk.bidit.presentation.viewModel.SearchKeywordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SearchKeywordFragment :
    BaseFragment<FragmentSearchKeywordBinding>(R.layout.fragment_search_keyword) {
    // sp에 저장된 검색 리스트를 불러온다.
    private val keywordViewModel: SearchKeywordViewModel by viewModels()
    private val merchandiseViewModel by viewModels<MerchandiseViewModel>()
    private val merchandiseAdapter by lazy { CommonMerchandiseListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initEvent()
    }

    override fun init() {
        //sp에 저장된 키워드
        val keywordList = SearchKeywordManager(requireContext()).getKeyword()
        val keywordListSize = keywordList.size

        //뷰모델에 적용시킨다.
        keywordViewModel.initKeywordList(keywordList)
        observeKeywordList()


        binding.apply {
            //만약 sp에 데이터가 없다면 데이터 없음을 표시한다.
            if (keywordListSize == 0) {
                lyNoKeyword.visibility = View.VISIBLE
                lyNoKeyword.bringToFront()

                tvRecentSearchKeywordTitle.visibility = View.GONE
                tvAllDelete.visibility = View.GONE
                rvSearchKeywordList.visibility = View.GONE
            }
            //데이터가 존재 할때만 리스트를 가져온다
            else {
                merchandiseViewModel.getKeywordItemList(keywordList[0], "latestOrder")
                observeMerchandiseList()
            }

            rvSearchKeywordList.apply {
                //리스트 초기화
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter =
                    SearchKeywordListAdapter(
                        requireContext(),
                        keywordList,
                        onClickDeleteKeyword = { keywordViewModel.deleteKeyword(it) },
                        onClickItem = {
                            navigate(SearchKeywordFragmentDirections.actionSearchKeywordFragmentToSearchResultFragment(it))
                        }
                    )
            }

            rvRecentSearchItemList.apply {
                layoutManager =
                    GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
                adapter = merchandiseAdapter
                addItemDecoration(GridRecyclerViewDeco(12, 12, 0, 37))
            }
        }
    }

    override fun initEvent() {
        binding.apply {
            tvAllDelete.setOnClickListener {
                SearchKeywordManager(requireContext()).removeAllKeyword()
                keywordViewModel.deleteAllKeyword()
            }
            btnBack.setOnClickListener {

            }
            ivEditTextDelete.setOnClickListener {
                binding.editSearch.setText("")
            }
            editSearch.setOnEditorActionListener { view, imeOption, _ ->
                if (imeOption == EditorInfo.IME_ACTION_SEARCH && view?.text?.toString() != "") {
                    //sp에 추가 -> 베이스는 원래가지고 있는 리스트
                    SearchKeywordManager(requireContext()).addKeyword(
                        (binding.rvSearchKeywordList.adapter as SearchKeywordListAdapter).keywordList,
                        binding.editSearch.text.toString()
                    )
                    //바뀐 리스트를 적용해야함
                    keywordViewModel.initKeywordList(SearchKeywordManager(requireContext()).getKeyword())

                    //키워드를 번들에 담아서 주고 결과 프래그먼트로 변경
                    navigate(
                        SearchKeywordFragmentDirections.actionSearchKeywordFragmentToSearchResultFragment(
                            view?.text?.toString()
                        )
                    )
                    //input 텍스트 초기화
                    binding.editSearch.setText("")
                }
                true
            }
            editSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    //텍스트가 존재할 때만 삭제 버튼을 보이게 함.
                    if (s?.toString() != "") {
                        binding.ivEditTextDelete.visibility = View.VISIBLE
                    } else {
                        binding.ivEditTextDelete.visibility = View.GONE
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
        }
    }


    private fun observeKeywordList() {
        //라이브 데이터에 변화가 있으면 실행된다.
        keywordViewModel.keywordLiveData.observe(viewLifecycleOwner) {
            //새로 바뀐 리스트를 리사이클러뷰에 적용
            (binding.rvSearchKeywordList.adapter as SearchKeywordListAdapter).setKeyword(it)
            //이 리스트의 크기가 0이라면 데이터 없음을 표시
            if (it.size == 0) {
                binding.apply {
                    lyNoKeyword.visibility = View.VISIBLE
                    lyNoKeyword.bringToFront()

                    tvRecentSearchKeywordTitle.visibility = View.GONE
                    tvAllDelete.visibility = View.GONE
                    rvSearchKeywordList.visibility = View.GONE
                }
            }
        }
    }

    private fun observeMerchandiseList() {
        //fragment는 viewLifeCycleOwner로
        merchandiseViewModel.itemList.observe(viewLifecycleOwner) { response ->
            when (response) {
                //서버 연결 대기중
                is ViewState.Loading -> {
                    Log.d("Merchandise Loading", "Loading GET merchandise list")
                }
                //아이템 가져오기 성공
                is ViewState.Success -> {
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
                    merchandiseAdapter.submitList(emptyList())
                    Log.d("Merchandise Failure", "Fail GET merchandise list")
                }
            }
        }
    }

    //1
    override fun onDestroyView() {
        super.onDestroyView()
    }

    //2
    override fun onDestroy() {
        super.onDestroy()
    }

    //3
    override fun onDetach() {
        super.onDetach()
    }
}