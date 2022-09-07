package com.alexk.bidit.presentation.ui.item

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexk.bidit.GetItemInfoQuery
import com.alexk.bidit.GlobalApplication
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.bidding.BiddingMerchandiseImgPageAdapter
import com.alexk.bidit.common.adapter.bidding.BiddingUserAdapter
import com.alexk.bidit.common.util.ErrorOwnItemBidding
import com.alexk.bidit.databinding.FragmentBiddingBinding
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.ui.item.dialog.*
import com.alexk.bidit.presentation.viewModel.BiddingViewModel
import com.alexk.bidit.presentation.viewModel.ItemViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class BiddingFragment : BaseFragment<FragmentBiddingBinding>(R.layout.fragment_bidding) {

    private val itemViewModel by viewModels<ItemViewModel>()
    private val bidViewModel by viewModels<BiddingViewModel>()
    private val itemId by lazy { activity?.intent?.getIntExtra("itemId", 0) }
    private lateinit var itemInfo: GetItemInfoQuery.GetItem
    private var bidPrice = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun init() {
        observeItemInfo()
        observeBiddingInfo()
        Log.d("itemId", "$itemId")
        itemViewModel.getItemInfo(itemId!!)
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
                val biddingMoreInfoDialog = BiddingBoardMoreInfoDialog {
                    //0 -> 수정, 1 -> 삭제
                    if (it == 0) {
                        Log.d("123","123")
                    } else {
                        itemViewModel.updateItemStatus(itemId!!,4)
                    }
                }
                biddingMoreInfoDialog.arguments = Bundle().apply {
                    this.putInt("itemId", itemId!!)
                }
                biddingMoreInfoDialog.show(childFragmentManager, biddingMoreInfoDialog.tag)
            }
            //상태변경 Dialog
            tvBiddingStatus.setOnClickListener {
                val dialog =
                    BiddingBoardStatusDialog(
                        requireContext(),
                        itemInfo?.status!!,
                        this@BiddingFragment.itemInfo.cPrice
                    ) {
                        itemViewModel.updateItemStatus(itemId!!, it)
                    }
                dialog.setCanceledOnTouchOutside(true)
                dialog.show()
                dialog.window?.setLayout(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
            }
            btnBidding.setOnClickListener {
                val biddingFragment = BiddingBidDialog {
                    //고차 함수로 입력한 입찰가 받아옴
                    bidPrice = it
                    bidViewModel.controlBid(itemId!!, bidPrice, 0)
                }

                //bidding price
                biddingFragment.arguments = Bundle().apply {

                    var price = itemInfo?.cPrice

                    if (itemInfo?.cPrice == null) {
                        price = itemInfo?.sPrice
                    }
                    this.putInt("currentPrice", price!!)
                    this.putInt("bidPrice", 1000)
                }
                biddingFragment.show(childFragmentManager, biddingFragment.tag)
            }
            btnImmediatePurchase.setOnClickListener {
                val dialog =
                    BiddingBidImmediatePurchaseDialog(requireContext(), itemInfo?.buyNow!!)
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

    private fun setBiddingInfoUI(getItemInfo: GetItemInfoQuery.GetItem) {
        //UI 작업 진행
        binding.apply {

            itemInfo = getItemInfo
//            bidSellingUserInfo = getItemInfo

            vpMerchandiseImg.apply {
                adapter =
                    BiddingMerchandiseImgPageAdapter(
                        this@BiddingFragment,
                        getItemInfo.image
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

    private fun setMyBiddingInfoUI(getItemInfo: GetItemInfoQuery.GetItem) {
        //UI 작업 진행
        binding.apply {

            lyStatus.visibility = View.VISIBLE
            ivMoreInfo.visibility = View.VISIBLE

            btnBidding.visibility = View.GONE
            btnImmediatePurchase.visibility = View.GONE

            itemInfo = getItemInfo
//            bidSellingUserInfo = getItemInfo

            if (getItemInfo.status == 3) {
                binding.tvBiddingStatus.text = "종료"
                binding.tvBiddingStatus.isClickable = false
            }

            vpMerchandiseImg.apply {
                adapter =
                    BiddingMerchandiseImgPageAdapter(
                        this@BiddingFragment,
                        getItemInfo.image
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

    private fun observeItemInfo() {
        itemViewModel.itemInfo.observe(viewLifecycleOwner) { response ->
            when (response) {
                //서버 연결 대기중
                is ViewState.Loading -> {
                    loadingDialogShow()
                    binding.svMain.visibility = View.INVISIBLE
                    Log.d("bidding Loading", "Loading GET bidding info")
                }
                //아이템 가져오기 성공
                is ViewState.Success -> {
                    loadingDialogDismiss()
                    Log.d("bidding Success", "Success GET bidding info")
                    //데이터 연동 작업 필요
                    val result = response.value?.data?.getItem
                    itemInfo = result!!
                    initEvent()
                    //내 게시글
                    if (result.userId == GlobalApplication.instance.getUserId()) {
                        setMyBiddingInfoUI(itemInfo)
                    }
                    //다른 사람 게시글
                    else {
                        setBiddingInfoUI(itemInfo)
                    }
                    binding.svMain.visibility = View.VISIBLE
                }
                is ViewState.Error -> {
                    loadingDialogDismiss()
                    Log.d("bidding Failure", "Fail GET bidding info")
                }
            }
        }
        itemViewModel.updateItem.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    loadingDialogShow()
                    Log.d("bidding Loading", "Loading GET bidding info")
                }
                is ViewState.Success -> {
                    loadingDialogDismiss()
                    Log.d("bidding Success", "Success GET bidding info")
                    if(response.value?.data?.updateItem?.status == 4){
                        activity?.finish()
                    }
                    else{
                        itemViewModel.getItemInfo(itemId!!)
                    }
                }
                is ViewState.Error -> {
                    loadingDialogDismiss()
                    Log.d("bidding Failure", "Fail GET bidding info")
                }
            }
        }
    }

    private fun observeBiddingInfo() {
        //fragment는 viewLifeCycleOwner로
        bidViewModel.bidCompleteInfo.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    loadingDialogShow()
                    Log.d("bidding Loading", "Loading POST bidding info")
                }
                is ViewState.Success -> {
                    loadingDialogDismiss()
                    Log.d("bidding Success", "Success POST bidding info")
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
                        val dialog = BiddingBidAlreadyTopBidDialog(requireContext())
                        dialog.setCanceledOnTouchOutside(true)
                        dialog.show()
                        dialog.window?.setLayout(
                            WindowManager.LayoutParams.MATCH_PARENT,
                            WindowManager.LayoutParams.WRAP_CONTENT
                        )
                    }
                }
                is ViewState.Error -> {
                    loadingDialogDismiss()
                    Log.d("bidding Failure", "Fail POST bidding info")
                }
            }
        }
    }
}