package com.alexk.bidit.presentation.ui.bidding

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexk.bidit.GetBiddingInfoQuery
import com.alexk.bidit.GlobalApplication
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.bidding.BiddingMerchandiseImgPageAdapter
import com.alexk.bidit.common.adapter.bidding.BiddingUserAdapter
import com.alexk.bidit.common.util.ErrorOwnItemBidding
import com.alexk.bidit.databinding.FragmentBiddingBinding
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.ui.bidding.dialog.BiddingBidDialog
import com.alexk.bidit.presentation.ui.bidding.dialog.BiddingBidImmediatePurchaseDialog
import com.alexk.bidit.presentation.ui.bidding.dialog.BiddingBoardMoreInfoDialog
import com.alexk.bidit.presentation.ui.bidding.dialog.BiddingBoardStatusDialog
import com.alexk.bidit.presentation.viewModel.BiddingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class BiddingFragment : BaseFragment<FragmentBiddingBinding>(R.layout.fragment_bidding) {

    private val viewModel by viewModels<BiddingViewModel>()
    private val itemId by lazy { activity?.intent?.getIntExtra("itemId", 0) }
    private lateinit var getBiddingInfo: GetBiddingInfoQuery.GetBidding
    private var bidPrice = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun init() {
        observeBiddingInfo()
        Log.d("itemId", "$itemId")
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
            //더 보기
            ivMoreInfo.setOnClickListener {
                val biddingMoreInfoDialog = BiddingBoardMoreInfoDialog()
                biddingMoreInfoDialog.arguments = Bundle().apply {
                    this.putInt("itemId", itemId!!)
                }
                biddingMoreInfoDialog.show(childFragmentManager, biddingMoreInfoDialog.tag)
            }
            //상태변경 Dialog
            tvBiddingStatus.setOnClickListener {
                val dialog =
                    BiddingBoardStatusDialog(requireContext(), getBiddingInfo.item?.status!!)
                dialog.setCanceledOnTouchOutside(true)
                dialog.show()
                dialog.window?.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
            }
            btnBidding.setOnClickListener {
                val biddingFragment = BiddingBidDialog {
                    //고차 함수로 입력한 입찰가 받아옴
                    bidPrice = it
                    viewModel.doBid(itemId!!, bidPrice)
                }

                //bidding price
                biddingFragment.arguments = Bundle().apply {
                    this.putInt("currentPrice", getBiddingInfo.item?.cPrice!!)
                    this.putInt("bidPrice", 1000)
                }
                biddingFragment.show(childFragmentManager, biddingFragment.tag)
            }
            btnImmediatePurchase.setOnClickListener {
                val dialog =
                    BiddingBidImmediatePurchaseDialog(requireContext(), getBiddingInfo.item?.buyNow)
                dialog.setCanceledOnTouchOutside(true)
                dialog.show()
                dialog.window?.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
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

    private fun setMyBiddingInfoUI(getBiddingInfo: GetBiddingInfoQuery.GetBidding?) {
        //UI 작업 진행
        binding.apply {

            lyStatus.visibility = View.VISIBLE
            ivMoreInfo.visibility = View.VISIBLE

            btnBidding.visibility = View.GONE
            btnImmediatePurchase.visibility = View.GONE

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
                    if (result?.isNotEmpty() == true) {
                        getBiddingInfo = result[0]!!
                        initEvent()
                        //내 게시글
                        if (result[0]?.user?.id == GlobalApplication.id) {
                            setMyBiddingInfoUI(getBiddingInfo)
                        }

                        //다른 사람 게시글
                        else {
                            setBiddingInfoUI(getBiddingInfo)
                        }
                    } else {
                        Log.d("Bidding Failure", "Success GET bidding info, but not data")
                    }
                }
                is ViewState.Error -> {
                    Log.d("Bidding Failure", "Fail GET bidding info")
                }
            }
        }
        viewModel.bidCompleteInfo.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    Log.d("Bidding Loading", "Loading POST bidding info")
                }
                is ViewState.Success -> {
                    Log.d("Bidding Success", "Success POST bidding info")
                    //성공
                    val result = response.value?.data?.bid
                    if (result != null && response.value.errors?.get(0)?.message != ErrorOwnItemBidding) {
                        navigate(
                            BiddingFragmentDirections.actionBiddingFragmentToBiddingCompleteFragment(
                                bidPrice,
                                itemId!!
                            )
                        )
                    } else {
                        //내가 더 많은 가격을 bid함
                    }
                }
                is ViewState.Error -> {
                    Log.d("Bidding Failure", "Fail POST bidding info")
                }
            }
        }
    }
}