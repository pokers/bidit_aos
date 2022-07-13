package com.alexk.bidit.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexk.bidit.R
import com.alexk.bidit.databinding.FragmentCommonBannerBinding
import com.alexk.bidit.databinding.FragmentCommonMerchandiseListBinding

class HomeBannerFragment : Fragment() {

    private val tempBannerList = listOf(
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background,
    )

    private lateinit var binding: FragmentCommonBannerBinding
    private val bannerPosition by lazy { arguments?.getInt("bannerPosition") }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommonBannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ivBanner.setImageResource(tempBannerList[bannerPosition!!])
        }
    }
}