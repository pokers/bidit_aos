package com.alexk.bidit.presentation.ui.bid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexk.bidit.GlobalApplication
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.bidding.BiddingMerchandiseImgPageAdapter
import com.alexk.bidit.common.adapter.bidding.BiddingUserAdapter
import com.alexk.bidit.common.util.setLoadingDialog
import com.alexk.bidit.common.util.showLongToastMessage
import com.alexk.bidit.common.util.value.*
import com.alexk.bidit.common.util.value.ApolloErrorConstant.ErrorLowPriceBidding
import com.alexk.bidit.common.util.value.KeyConstants.FRAGMENT_KEY_BID_PRICE
import com.alexk.bidit.common.util.value.KeyConstants.FRAGMENT_KEY_CURRENT_BID_PRICE
import com.alexk.bidit.common.util.value.KeyConstants.FRAGMENT_KEY_ITEM_ID
import com.alexk.bidit.common.view.ViewState
import com.alexk.bidit.databinding.FragmentBiddingBinding
import com.alexk.bidit.domain.entity.item.ItemBasicEntity
import com.alexk.bidit.domain.entity.item.img.ItemImgEntity
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.ui.bid.bidding.alreadyTop.BiddingBidAlreadyTopBidDialog
import com.alexk.bidit.presentation.ui.bid.bidding.BiddingBidDialog
import com.alexk.bidit.presentation.ui.bid.bidding.immediatePurchase.BiddingBidImmediatePurchaseDialog
import com.alexk.bidit.presentation.ui.bid.seller.BiddingBoardMoreInfoDialog
import com.alexk.bidit.presentation.ui.bid.seller.status.BiddingItemStatusDialog
import com.alexk.bidit.presentation.ui.home.HomeActivity
import com.alexk.bidit.presentation.viewModel.BiddingViewModel
import com.alexk.bidit.presentation.viewModel.ItemViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class BiddingFragment : BaseFragment<FragmentBiddingBinding>(R.layout.fragment_bidding) {
    //0 : registed, 1: ongoing, 2: sold, 3: end, 4: cancel
    private val itemViewModel by viewModels<ItemViewModel>()
    private val bidViewModel by viewModels<BiddingViewModel>()
    private val itemId by lazy { activity?.intent?.getIntExtra(FRAGMENT_KEY_ITEM_ID, 0) }
    private lateinit var itemInfo: ItemBasicEntity
    private var bidPrice = 0
    private var currentStatus = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        addBackButtonEvent()
        addSellerUsingButtonEvent()
    }

    private fun init() {
        observeItemInfo()
        observeAfterBiddInfo()
        observePostInfo()
        itemViewModel.getItemInfo(itemId!!)
    }

    private fun getItemStatus(status: Int): ItemStatus {
        return when (status) {
            0 -> ItemStatus.REGISTED
            1 -> ItemStatus.ONGOING
            2 -> ItemStatus.SOLD
            3 -> ItemStatus.END
            4 -> ItemStatus.CANCEL
            else -> throw NoSuchElementException("No search item status")
        }
    }

    private fun addBackButtonEvent() {
        binding.ivBack.setOnClickListener {
            activity?.finish()
        }
    }

    private fun setPostStatus(type: PostProcessType) {
        when (type) {
            PostProcessType.DELETE -> {
                itemViewModel.updateItemStatus(itemId!!, ItemStatus.END.status)
            }
            PostProcessType.MODIFY -> {
                //modify post
            }
        }
    }

    private fun addSellerUsingButtonEvent() {
        binding.ivMoreInfo.setOnClickListener {
            val biddingMoreInfoDialog = BiddingBoardMoreInfoDialog { setPostStatus(it) }
            biddingMoreInfoDialog.show(childFragmentManager, biddingMoreInfoDialog.tag)
        }
    }

    private fun showBiddingItemStatusDialog() {
        val dialog =
            BiddingItemStatusDialog(
                requireContext(),
                status = getItemStatus(itemInfo.status!!),
                cPrice = itemInfo.cPrice
            ) {
                itemViewModel.updateItemStatus(itemId!!, it.status)
            }
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        dialog.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun addItemStatusEvent() {
        binding.apply {
            tvBiddingStatus.setOnClickListener {
                showBiddingItemStatusDialog()
            }
        }
    }

    private fun setCurrentBidPrice(): Int {
        return itemInfo.cPrice ?: itemInfo.sPrice!!
    }

    private fun showBiddingDialog() {
        val biddingDialog = BiddingBidDialog { bidPrice ->
            bidViewModel.controlBid(itemId!!, bidPrice, BidStatus.VALID)
        }
        biddingDialog.arguments = Bundle().apply {
            this.putInt(FRAGMENT_KEY_CURRENT_BID_PRICE, setCurrentBidPrice())
            this.putInt(FRAGMENT_KEY_BID_PRICE, 1000)
        }
        biddingDialog.show(childFragmentManager, biddingDialog.tag)
    }

    private fun addBidButtonEvent() {
        binding.btnBidding.setOnClickListener {
            showBiddingDialog()
        }
    }

    private fun addImmediatePurchaseEvent() {
        binding.btnImmediatePurchase.setOnClickListener {
            showImmediatePurchaseDialog()
        }
    }

    private fun showImmediatePurchaseDialog() {
        val dialog =
            BiddingBidImmediatePurchaseDialog(requireContext(), itemInfo.buyNow!!, itemInfo.itemUserInfo?.id!!)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    private fun setBiddingInfoUI(itemInfo: ItemBasicEntity) {
        initItemImg(itemInfo.itemImgList!!)
        addBidButtonEvent()
        addImmediatePurchaseEvent()
        binding.apply {
            this.itemInfo = itemInfo
            initBiddingList()
            svMain.visibility = View.VISIBLE
        }
    }

    private fun setOptionMenuVisibility() {
        binding.apply {
            lyStatus.visibility = View.VISIBLE
            ivMoreInfo.visibility = View.VISIBLE
            btnBidding.visibility = View.GONE
            btnImmediatePurchase.visibility = View.GONE
        }
    }

    private fun initItemImg(imgList: List<ItemImgEntity>) {
        binding.apply {
            vpMerchandiseImg.adapter =
                BiddingMerchandiseImgPageAdapter(this@BiddingFragment, imgList)
            ciMerchandiseImg.setViewPager(vpMerchandiseImg)
        }
    }

    private fun initBiddingList() {
        binding.apply {
            rvRecentBiddingUser.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = BiddingUserAdapter()
            }
        }
    }

    private fun setMyBiddingInfoUI(myItemInfo: ItemBasicEntity) {
        setOptionMenuVisibility()
        initItemImg(myItemInfo.itemImgList!!)
        addItemStatusEvent()
        binding.apply {
            itemInfo = myItemInfo
            initBiddingList()
            //if item status is 3, status change view's clickable must be false
            if (myItemInfo.status == 3) {
                binding.tvBiddingStatus.text = "종료"
                binding.tvBiddingStatus.isClickable = false
            }
            svMain.visibility = View.VISIBLE
        }
    }

    private fun observePostInfo() {
        itemViewModel.updateItem.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    requireContext().setLoadingDialog(true)
                }
                is ViewState.Success -> {
                    requireContext().showLongToastMessage("게시글이 삭제 되었습니다.")
                    requireContext().setLoadingDialog(false)
                    activity?.finish()
                    startActivity(Intent(requireContext(), HomeActivity::class.java))
                }
                is ViewState.Error -> {
                    requireContext().setLoadingDialog(false)
                }
            }
        }
    }

    private fun observeItemInfo() {
        itemViewModel.itemInfo.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    requireContext().setLoadingDialog(true)
                }
                is ViewState.Success -> {
                    requireContext().setLoadingDialog(false)
                    val result = response.value
                    itemInfo = result!!
                    currentStatus = result.status!!
                    if (result.itemUserInfo?.id == GlobalApplication.userId) {
                        setMyBiddingInfoUI(itemInfo)
                    } else {
                        setBiddingInfoUI(itemInfo)
                    }
                }
                is ViewState.Error -> {
                    requireContext().setLoadingDialog(false)
                }
            }
        }
    }

    private fun showAlreadyTopBidDialog() {
        val dialog = BiddingBidAlreadyTopBidDialog(requireContext())
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun observeAfterBiddInfo() {
        bidViewModel.bidCompleteInfo.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    requireContext().setLoadingDialog(true)
                }
                is ViewState.Success -> {
                    requireContext().setLoadingDialog(false)
                    //if bid success, reutn bidStatus.VALID
                    if (response.value is BidStatus) {
                        navigate(
                            BiddingFragmentDirections.actionBiddingFragmentToBiddingCompleteFragment(
                                bidPrice,
                                itemId!!
                            )
                        )
                    }
                }
                is ViewState.Error -> {
                    requireContext().setLoadingDialog(false)
                    if (response.message == ErrorLowPriceBidding) {
                        showAlreadyTopBidDialog()
                    }
                }
            }
        }
    }
}