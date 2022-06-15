package com.alexk.bidit.presentation.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.search.SearchKeywordListAdapter
import com.alexk.bidit.data.sharedPreference.SearchKeywordManager
import com.alexk.bidit.databinding.FragmentSearchResultBinding
import com.alexk.bidit.presentation.base.BaseFragment

class SearchResultFragment :
    BaseFragment<FragmentSearchResultBinding>(R.layout.fragment_search_result),
    TextWatcher, TextView.OnEditorActionListener {

    private val args: SearchResultFragmentArgs by navArgs()

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
            editSearch.addTextChangedListener(this@SearchResultFragment)
            editSearch.setOnEditorActionListener(this@SearchResultFragment)
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (text?.toString() != "") {
            binding.ivEditTextDelete.visibility = View.VISIBLE
        } else {
            binding.ivEditTextDelete.visibility = View.GONE
        }
    }

    override fun afterTextChanged(p0: Editable?) {

    }

    override fun onEditorAction(view: TextView?, imeOption: Int, p2: KeyEvent?): Boolean {
        //텍스트가 있어야하고 검색버튼을 누르면?
        if (imeOption == EditorInfo.IME_ACTION_SEARCH && view?.text?.toString() != "") {
            //sp에 추가 -> 베이스는 원래가지고 있는 리스트
            SearchKeywordManager(requireContext()).addKeyword(
                SearchKeywordManager(requireContext()).getKeyword(),
                binding.editSearch.text.toString()
            )
        }
        return true
    }
}