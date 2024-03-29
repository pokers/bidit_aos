package com.alexk.bidit.presentation.ui.search

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
import com.alexk.bidit.common.adapter.item.ItemListAdapter
import com.alexk.bidit.common.adapter.search.SearchKeywordListAdapter
import com.alexk.bidit.common.util.setLoadingDialog
import com.alexk.bidit.common.view.GridRecyclerViewDeco
import com.alexk.bidit.common.util.sharePreference.SearchKeywordManager
import com.alexk.bidit.databinding.FragmentSearchKeywordBinding
import com.alexk.bidit.common.view.ViewState
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.viewModel.ItemViewModel
import com.alexk.bidit.presentation.viewModel.SearchViewModel
import com.alexk.bidit.type.CursorType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SearchKeywordFragment :
    BaseFragment<FragmentSearchKeywordBinding>(R.layout.fragment_search_keyword) {
    // sp에 저장된 검색 리스트를 불러온다.
    private val viewModel: SearchViewModel by viewModels()
    private val merchandiseViewModel by viewModels<ItemViewModel>()
    private val merchandiseAdapter by lazy { ItemListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initEvent()
    }

    private fun init() {
        //sp에 저장된 키워드
        val keywordList = SearchKeywordManager.getKeyword()
        val keywordListSize = keywordList.size

        //뷰모델에 적용시킨다.
        viewModel.initKeywordList(keywordList)
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
                merchandiseViewModel.getKeywordItemList(keywordList[0], CursorType.createdAt)
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
                        onClickDeleteKeyword = { viewModel.deleteKeyword(it) },
                        onClickItem = {
                            navigate(
                                SearchKeywordFragmentDirections.actionSearchKeywordFragmentToSearchResultFragment(
                                    it
                                )
                            )
                        }
                    )
            }

            rvRecentSearchItemList.apply {
                val gridLayoutManager =
                    GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
                layoutManager = gridLayoutManager
                adapter = merchandiseAdapter
                addItemDecoration(GridRecyclerViewDeco(12, 12, 0, 37))
            }
        }
    }

    private fun initEvent() {
        binding.apply {
            tvAllDelete.setOnClickListener {
                SearchKeywordManager.removeAllKeyword()
                viewModel.deleteAllKeyword()
            }
            btnBack.setOnClickListener {

            }
            ivEditTextDelete.setOnClickListener {
                binding.editSearch.setText("")
            }
            editSearch.setOnEditorActionListener { view, imeOption, _ ->
                if (imeOption == EditorInfo.IME_ACTION_SEARCH && view?.text?.toString() != "") {
                    //sp에 추가 -> 베이스는 원래가지고 있는 리스트
                    SearchKeywordManager.addKeyword(
                        (binding.rvSearchKeywordList.adapter as SearchKeywordListAdapter).keywordList,
                        binding.editSearch.text.toString()
                    )
                    //바뀐 리스트를 적용해야함
                    viewModel.initKeywordList(SearchKeywordManager.getKeyword())

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
        viewModel.keywordLiveData.observe(viewLifecycleOwner) {
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
                    context?.setLoadingDialog(true)
                    Log.d(TAG, "Loading GET merchandise list")
                }
                //아이템 가져오기 성공
                is ViewState.Success -> {
                    context?.setLoadingDialog(false)
//                    Log.d(TAG, "Success GET merchandise list")
//                    //리사이클러뷰 어댑터 연결
//                    val result = typeCastItemQueryToItemEntity(response.value?.data?.getItemList?.edges)
//                    if (result.size == 0) {
//                        Log.d(TAG, "No merchandise data")
//                        merchandiseAdapter.submitList(emptyList())
//                        binding.lyNoKeyword.visibility = View.VISIBLE
//                    } else {
//                        merchandiseAdapter.onItemClicked =
//                            {
//                                val intent = Intent(requireContext(), BiddingActivity::class.java)
//                                intent.putExtra("itemId", it)
//                                startActivity(intent)
//                            }
//                        merchandiseAdapter.submitList(result)
//                    }
                }
                //서버 연결 실패(만료) -> 재발급 요청
                is ViewState.Error -> {
                    context?.setLoadingDialog(false)
                    merchandiseAdapter.submitList(emptyList())
                    Log.d(TAG, "Fail GET merchandise list")
                }
            }
        }
    }

    companion object{
        private const val TAG = "SearchKeywordFragment..."
    }
}