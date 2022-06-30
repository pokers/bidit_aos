package com.alexk.bidit.presentation.ui.bidding

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.bidding.BiddingMerchandiseImgPageAdapter
import com.alexk.bidit.common.adapter.bidding.BiddingUserAdapter
import com.alexk.bidit.databinding.FragmentBiddingBinding
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.tempResponse.BiddingUserResponse

class BiddingFragment : BaseFragment<FragmentBiddingBinding>(R.layout.fragment_bidding),
    View.OnClickListener {

    private val tempImgList = listOf(
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background
    )

    private val tempBiddingUserList = listOf(
        BiddingUserResponse("구매자이름1", "3분전", 30000, 1000, "temp"),
        BiddingUserResponse("구매자이름2", "3분전", 30000, 1000, "temp"),
        BiddingUserResponse("구매자이름3", "3분전", 30000, 1000, "temp"),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initEvent()
    }

    override fun init() {
        binding.apply {
            vpMerchandiseImg.adapter =
                BiddingMerchandiseImgPageAdapter(this@BiddingFragment, tempImgList)
            ciMerchandiseImg.setViewPager(vpMerchandiseImg)


            rvRecentBiddingUser.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvRecentBiddingUser.adapter = BiddingUserAdapter(requireContext(), tempBiddingUserList)
        }
    }

    override fun initEvent() {
        binding.apply {
            ivBack.setOnClickListener(this@BiddingFragment)
            ivLike.setOnClickListener(this@BiddingFragment)
            ivShare.setOnClickListener(this@BiddingFragment)
            btnBidding.setOnClickListener(this@BiddingFragment)
            btnImmediatePurchase.setOnClickListener(this@BiddingFragment)
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.ivBack -> {
                activity?.finish()
            }
            binding.ivLike -> {
                //좋아요, 찜
            }
            binding.ivShare -> {
                //공유
            }
            binding.btnBidding -> {

                val biddingFragment = BiddingDialog {
                    when (it) {
                        0 -> Toast.makeText(requireContext(), "0 콜백", Toast.LENGTH_SHORT).show()
                        1 -> Toast.makeText(requireContext(), "0 콜백", Toast.LENGTH_SHORT).show()
                    }
                }
                biddingFragment.arguments.apply {
                    Bundle().putInt("price", 1000)
                }
                biddingFragment.show(childFragmentManager, biddingFragment.tag)
            }
            binding.btnImmediatePurchase -> {
                val dialog = BiddingImmediatePurchaseDialog(requireContext(), 3000)
                dialog.setCanceledOnTouchOutside(true)
                dialog.show()
                dialog.window?.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )

            }
        }
    }
}