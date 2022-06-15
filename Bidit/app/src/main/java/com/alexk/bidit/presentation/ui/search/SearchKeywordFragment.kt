package com.alexk.bidit.presentation.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.search.SearchKeywordListAdapter
import com.alexk.bidit.data.sharedPreference.SearchKeywordManager
import com.alexk.bidit.data.sharedPreference.keyword
import com.alexk.bidit.databinding.FragmentSearchKeywordBinding
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.viewModel.SearchKeywordViewModel

class SearchKeywordFragment :
    BaseFragment<FragmentSearchKeywordBinding>(R.layout.fragment_search_keyword),
    View.OnClickListener, TextView.OnEditorActionListener, TextWatcher {
    // sp에 저장된 검색 리스트를 불러온다.
    private val keywordViewModel: SearchKeywordViewModel by viewModels()

    //아니 이게 왜..? pause개념이 아니지..?
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initEvent()
    }

    override fun init() {
        binding.apply {
            //sp에 저장된 키워드
            val keywordList = SearchKeywordManager(requireContext()).getKeyword()
            val keywordListSize = keywordList.size

            //뷰모델에 적용시킨다.
            keywordViewModel.initKeywordList(keywordList)

            //만약 sp에 데이터가 없다면 데이터 없음을 표시한다.
            if (keywordListSize == 0) {
                lyNoKeyword.visibility = View.VISIBLE
                lyNoKeyword.bringToFront()

                tvRecentSearchKeyword.visibility = View.GONE
                tvAllDelete.visibility = View.GONE
                rvSearchKeywordList.visibility = View.GONE
            }

            //리스트 초기화
            rvSearchKeywordList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvSearchKeywordList.adapter =
                SearchKeywordListAdapter(
                    requireContext(),
                    keywordList,
                    onClickDeleteKeyword = { keywordViewModel.deleteKeyword(it) },
                    onClickItem = {
                        navigate(
                            SearchKeywordFragmentDirections.actionSearchKeywordFragmentToSearchResultFragment(
                                it
                            )
                        )
                    }
                )

            //라이브 데이터에 변화가 있으면 실행된다.
            keywordViewModel.keywordLiveData.observe(viewLifecycleOwner, Observer {
                //새로 바뀐 리스트를 리사이클러뷰에 적용
                (binding.rvSearchKeywordList.adapter as SearchKeywordListAdapter).setKeyword(it)
                //이 리스트의 크기가 0이라면 데이터 없음을 표시
                if (it.size == 0) {
                    binding.apply {
                        lyNoKeyword.visibility = View.VISIBLE
                        lyNoKeyword.bringToFront()

                        tvRecentSearchKeyword.visibility = View.GONE
                        tvAllDelete.visibility = View.GONE
                        rvSearchKeywordList.visibility = View.GONE
                    }
                }
            })
        }
    }

    override fun initEvent() {
        binding.apply {
            tvAllDelete.setOnClickListener(this@SearchKeywordFragment)
            btnBack.setOnClickListener(this@SearchKeywordFragment)
            ivEditTextDelete.setOnClickListener(this@SearchKeywordFragment)
            editSearch.setOnEditorActionListener(this@SearchKeywordFragment)
            editSearch.addTextChangedListener(this@SearchKeywordFragment)
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.tvAllDelete -> {
                SearchKeywordManager(requireContext()).removeAllKeyword()
                keywordViewModel.deleteAllKeyword()
            }
            //종료
            binding.btnBack -> {
                activity?.finish()
            }
            binding.ivEditTextDelete -> {
                binding.editSearch.setText("")
            }
        }
    }

    override fun onEditorAction(view: TextView?, imeOption: Int, p2: KeyEvent?): Boolean {
            //텍스트가 있어야하고 검색버튼을 누르면?
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
            return true
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        //텍스트가 존재할 때만 삭제 버튼을 보이게 함.
        if (p0?.toString() != "") {
            binding.ivEditTextDelete.visibility = View.VISIBLE
        } else {
            binding.ivEditTextDelete.visibility = View.GONE
        }
    }

    override fun afterTextChanged(p0: Editable?) {

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