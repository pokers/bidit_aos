package com.alexk.bidit.presentation.ui.bidding

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexk.bidit.GetBiddingInfoQuery
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.bidding.BiddingMerchandiseImgPageAdapter
import com.alexk.bidit.common.adapter.bidding.BiddingUserAdapter
import com.alexk.bidit.databinding.FragmentBiddingBinding
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.viewModel.BiddingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class BiddingFragment : BaseFragment<FragmentBiddingBinding>(R.layout.fragment_bidding) {

    private val viewModel by viewModels<BiddingViewModel>()
    private val itemId by lazy { activity?.intent?.getIntExtra("itemId", 0) }
    private lateinit var getBiddingInfo : GetBiddingInfoQuery.GetBidding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initEvent()
    }

    override fun init() {
        observeBiddingInfo()
        Log.d("itemId","$itemId")
        viewModel.retrieveBiddingInfo(itemId!!)
    }

    override fun initEvent() {
        binding.apply {
            ivBack.setOnClickListener {
                activity?.finish()
            }
            //좋아요, 찜
            ivLike.setOnClickListener {

            }
            //공유
            ivShare.setOnClickListener {

            }
            btnBidding.setOnClickListener {
                val biddingFragment = BiddingDialog {
                    when (it) {
                        0 -> Toast.makeText(requireContext(), "0 콜백", Toast.LENGTH_SHORT).show()
                        1 -> Toast.makeText(requireContext(), "0 콜백", Toast.LENGTH_SHORT).show()
                    }
                }
                //bidding price
                biddingFragment.arguments.apply {
                    Bundle().putInt("price", 1000)
                }
                biddingFragment.show(childFragmentManager, biddingFragment.tag)
            }
            btnImmediatePurchase.setOnClickListener {
                val dialog = BiddingImmediatePurchaseDialog(requireContext(), getBiddingInfo.item?.buyNow)
                dialog.setCanceledOnTouchOutside(true)
                dialog.show()
                dialog.window?.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
            }
        }
    }

    private fun observeBiddingInfo() {
        //fragment는 viewLifeCycleOwner로
        viewModel.biddingInfo.observe(viewLifecycleOwner) { response ->
            when (response) {
                //서버 연결 대기중
                is ViewState.Loading -> {
                    Log.d("Bidding Loading", "Loading GET bidding info")
                }
                //아이템 가져오기 성공
                is ViewState.Success -> {
                    Log.d("Bidding Success", "Success GET bidding info")
                    //데이터 연동 작업 필요
                    val result = response.value?.data?.getBidding
                    if(result?.isNotEmpty() == true){
                        getBiddingInfo = result[0]!!
                        setBiddingInfoUI(getBiddingInfo)
                    }
                    else{
                        Log.d("Bidding Failure", "Success GET bidding info, but not data")
                    }
                }
                //서버 연결 실패(만료) -> 재발급 요청
                is ViewState.Error -> {
                    Log.d("Bidding Failure", "Fail GET bidding info")
                }
            }
        }
    }

    private fun setBiddingInfoUI(getBiddingInfo: GetBiddingInfoQuery.GetBidding?) {
        //UI 작업 진행
        binding.apply {

            biddingInfo = getBiddingInfo?.item
            bidSellingUserInfo = getBiddingInfo?.user

            vpMerchandiseImg.apply {
                adapter =
                    BiddingMerchandiseImgPageAdapter(
                        this@BiddingFragment,
                        getBiddingInfo?.item?.image
                    )
            }
            ciMerchandiseImg.apply {
                setViewPager(vpMerchandiseImg)
            }

            rvRecentBiddingUser.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = BiddingUserAdapter()
            }
        }
    }
}