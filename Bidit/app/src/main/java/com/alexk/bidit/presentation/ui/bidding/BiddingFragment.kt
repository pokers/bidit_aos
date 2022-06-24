package com.alexk.bidit.presentation.ui.bidding

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.alexk.bidit.R
import com.alexk.bidit.databinding.FragmentBiddingBinding
import com.alexk.bidit.presentation.base.BaseFragment

class BiddingFragment : BaseFragment<FragmentBiddingBinding>(R.layout.fragment_bidding),
    View.OnClickListener {

    private val tempImgList = listOf(
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initEvent()
    }

    override fun init() {

    }

    override fun initEvent() {
        binding.apply {
            ivBack.setOnClickListener(this@BiddingFragment)
            ivLike.setOnClickListener(this@BiddingFragment)
            ivShare.setOnClickListener(this@BiddingFragment)
            btnBidding.setOnClickListener(this@BiddingFragment)
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
                val biddingFragment : BiddingBottomFragment = BiddingBottomFragment{
                    when(it){
                        0 -> Toast.makeText(requireContext(),"0 콜백",Toast.LENGTH_SHORT).show()
                        1 -> Toast.makeText(requireContext(),"0 콜백",Toast.LENGTH_SHORT).show()
                    }
                }
                biddingFragment.show(childFragmentManager, biddingFragment.tag)
            }
        }
    }
}