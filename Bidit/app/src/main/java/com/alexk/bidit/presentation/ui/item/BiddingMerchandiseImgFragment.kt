package com.alexk.bidit.presentation.ui.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.alexk.bidit.R
import com.alexk.bidit.databinding.FragmentCommonBannerBinding
import com.bumptech.glide.Glide

class BiddingMerchandiseImgFragment(private val imgUrl: String?) : Fragment() {

    lateinit var binding: FragmentCommonBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_common_banner, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext())
            .load(imgUrl)
            .into(binding.ivBanner)
    }
}