package com.alexk.bidit.presentation.ui.bid.img

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.alexk.bidit.R
import com.alexk.bidit.common.util.value.KeyConstants.FRAGMENT_KEY_ITEM_IMG_URL
import com.bumptech.glide.Glide

class BiddingMerchandiseImgFragment : Fragment() {

    lateinit var binding: com.alexk.bidit.databinding.FragmentBiddingBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bidding_banner, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgUrl = arguments?.getString(FRAGMENT_KEY_ITEM_IMG_URL)
        setItemImg(imgUrl!!)
    }

    private fun setItemImg(url : String){
        Glide.with(requireContext())
            .load(url)
            .into(binding.ivBanner)
    }
}