package com.alexk.bidit.presentation.ui.search

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.common.CommonMerchandiseListAdapter
import com.alexk.bidit.data.sharedPreference.SearchKeywordManager
import com.alexk.bidit.databinding.FragmentSearchResultBinding
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.ui.bidding.BiddingActivity
import com.alexk.bidit.presentation.viewModel.MerchandiseViewModel
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.overlay.BalloonOverlayRoundRect
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SearchResultFragment :
    BaseFragment<FragmentSearchResultBinding>(R.layout.fragment_search_result) {

    private val args: SearchResultFragmentArgs by navArgs()
    private var keyword = ""
    private var currentSortType = "latestOrder"
    private val merchandiseViewModel by viewModels<MerchandiseViewModel>()
    private val merchandiseAdapter by lazy { CommonMerchandiseListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initEvent()
    }

    override fun init() {
        //키워드 가져오기
        keyword = args.keyword!!

        observeMerchandiseList()
        merchandiseViewModel.getKeywordItemList(keyword, currentSortType)

        binding.apply {
            editSearch.setText(keyword)

            rvMerchandiseList.layoutManager = GridLayoutManager(requireContext(), 2)
            rvMerchandiseList.adapter = merchandiseAdapter

            lySort.setOnClickListener {
                makeBalloon(currentSortType).showAlignBottom(binding.ivSort)
            }
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
                keyword = binding.editSearch.text.toString()
                //텍스트가 있어야하고 검색버튼을 누르면?
                if (imeOption == EditorInfo.IME_ACTION_SEARCH && view?.text?.toString() != "") {
                    //sp에 추가 -> 베이스는 원래가지고 있는 리스트
                    SearchKeywordManager(requireContext()).addKeyword(
                        SearchKeywordManager(requireContext()).getKeyword(),
                        binding.editSearch.text.toString()
                    )
                    merchandiseViewModel.getKeywordItemList(keyword, currentSortType)
                }
                true
            }
            btnBack.setOnClickListener {
                navigate(
                    SearchResultFragmentDirections.actionSearchResultFragmentToSearchKeywordFragment(
                        keyword
                    )
                )
            }
            ivEditTextDelete.setOnClickListener { editSearch.setText("") }
        }
    }

    private fun observeMerchandiseList() {
        //fragment는 viewLifeCycleOwner로
        merchandiseViewModel.itemList.observe(viewLifecycleOwner) { response ->
            when (response) {
                //서버 연결 대기중
                is ViewState.Loading -> {
                    loadingDialogShow()
                    Log.d("Merchandise Loading", "Loading GET merchandise list")
                }
                //아이템 가져오기 성공
                is ViewState.Success -> {
                    loadingDialogDismiss()
                    Log.d("Merchandise Success", "Success GET merchandise list")
                    //리사이클러뷰 어댑터 연결
                    val result = response.value?.data?.getItemList?.edges
                    if (result?.size == 0) {
                        binding.lyNoResult.visibility = View.VISIBLE
                        binding.tvNoKeyword.text = "${keyword}에 대한 검색 결과가 없습니다."
                        merchandiseAdapter.submitList(emptyList())
                        Log.d("Empty Merchandise List", "No merchandise data")
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
                    loadingDialogDismiss()
                    merchandiseAdapter.submitList(emptyList())
                    Log.d("Merchandise Failure", "Fail GET merchandise list")
                }
            }
        }
    }

    private fun makeBalloon(sortType: String): Balloon {
        val balloon = Balloon.Builder(requireContext())
            .setLayout(R.layout.dialog_merchandise_sort_type)
            .setArrowSize(10)
            .setArrowOrientation(ArrowOrientation.TOP)
            .setArrowPosition(0.5f)
            .setMarginTop(12)
            .setElevation(0)
            .setIsVisibleOverlay(true)
            .setDismissWhenOverlayClicked(false)
            .setOverlayColor(ContextCompat.getColor(requireContext(), R.color.black_trans_40))
            .setCornerRadius(4f)
            .setOverlayPosition(Point(-1000, -1000))
            .setOverlayShape(BalloonOverlayRoundRect(0f, 0f))
            .setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            .setLifecycleOwner(this)
            .build()

        //정렬 방법 선택
        val latestTextView =
            balloon.getContentView().findViewById<TextView>(R.id.tv_sort_latest_order).apply {
                setOnClickListener {
                    currentSortType = "latestOrder"
                    balloon.dismiss()
                    binding.tvListSort.text = getString(R.string.category_latest_order)
                    merchandiseViewModel.getKeywordItemList(keyword, currentSortType)
                }
            }

        val deadlineTextView =
            balloon.getContentView().findViewById<TextView>(R.id.tv_sort_deadline_imminent).apply {
                setOnClickListener {
                    currentSortType = "deadline"
                    balloon.dismiss()
                    binding.tvListSort.text = getString(R.string.category_deadline_imminent)
                    merchandiseViewModel.getKeywordItemList(keyword, currentSortType)
                }
            }

        val popularTextView =
            balloon.getContentView().findViewById<TextView>(R.id.tv_sort_popular).apply {
                setOnClickListener {
                    currentSortType = "popular"
                    balloon.dismiss()
                    binding.tvListSort.text = getString(R.string.category_popular)
                    merchandiseViewModel.getKeywordItemList(keyword, currentSortType)
                }
            }

        //sortType에 따른 글자 변화
        when (sortType) {
            "latestOrder" -> {
                latestTextView.typeface =
                    ResourcesCompat.getFont(requireContext(), R.font.notosans_kr_bold)
                latestTextView.setTextColor(ResourcesCompat.getColor(resources, R.color.nero, null))
            }
            "deadline" -> {
                deadlineTextView.typeface =
                    ResourcesCompat.getFont(requireContext(), R.font.notosans_kr_bold)
                deadlineTextView.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.nero,
                        null
                    )
                )
            }
            "popular" -> {
                popularTextView.typeface =
                    ResourcesCompat.getFont(requireContext(), R.font.notosans_kr_bold)
                popularTextView.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.nero,
                        null
                    )
                )
            }
        }
        return balloon
    }
}