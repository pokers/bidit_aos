package com.alexk.bidit.presentation.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.merchandise.MerchandiseListAdapter
import com.alexk.bidit.common.adapter.search.SearchKeywordListAdapter
import com.alexk.bidit.data.sharedPreference.SearchKeywordManager
import com.alexk.bidit.databinding.FragmentSearchResultBinding
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.ui.bidding.BiddingActivity
import com.alexk.bidit.presentation.viewModel.MerchandiseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SearchResultFragment :
    BaseFragment<FragmentSearchResultBinding>(R.layout.fragment_search_result) {

    private val args: SearchResultFragmentArgs by navArgs()
    private val merchandiseViewModel by viewModels<MerchandiseViewModel>()
    private val merchandiseAdapter by lazy { MerchandiseListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        initEvent()
    }

    override fun init() {
        binding.apply {
            //앞서 넣어준 키워드 넣어주기
            editSearch.setText(args.keyword)

            //서버 통신 후 리스트 나오기
            //필터 적용
        }
    }

    override fun initEvent() {
        binding.apply {
            editSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.toString() != "") {
                        binding.ivEditTextDelete.visibility = View.VISIBLE
                    } else {
                        binding.ivEditTextDelete.visibility = View.GONE
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
            editSearch.setOnEditorActionListener { view, imeOption, _ ->
                //텍스트가 있어야하고 검색버튼을 누르면?
                if (imeOption == EditorInfo.IME_ACTION_SEARCH && view?.text?.toString() != "") {
                    //sp에 추가 -> 베이스는 원래가지고 있는 리스트
                    SearchKeywordManager(requireContext()).addKeyword(
                        SearchKeywordManager(requireContext()).getKeyword(),
                        binding.editSearch.text.toString()
                    )
                }
                true
            }
        }
    }

    private fun observeMerchandiseList() {
        //fragment는 viewLifeCycleOwner로
        merchandiseViewModel.cursorTypeItemList.observe(viewLifecycleOwner) { response ->
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
}