package com.alexk.bidit.presentation.ui.bidding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.alexk.bidit.R
import com.alexk.bidit.databinding.FragmentHomeBannerBinding

class BiddingMerchandiseImgFragment(val img: Int) : Fragment() {

    lateinit var binding: FragmentHomeBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_banner, container, false)
        binding.ivBanner.setImageResource(img)
        return binding.root
    }
}