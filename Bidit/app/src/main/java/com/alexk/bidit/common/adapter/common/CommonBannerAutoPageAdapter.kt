package com.alexk.bidit.common.adapter.common

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.R
import com.alexk.bidit.databinding.FragmentCommonBannerBinding

class CommonBannerAutoPageAdapter(val context: Context?, private val bannerList: List<Int>) :
    RecyclerView.Adapter<CommonBannerAutoPageAdapter.HomeBannerHolder>() {
    inner class HomeBannerHolder(private val binding: FragmentCommonBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(img: Int) {
            binding.ivBanner.setImageResource(img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBannerHolder {
        val view =
            DataBindingUtil.inflate<FragmentCommonBannerBinding>(LayoutInflater.from(parent.context), R.layout.fragment_common_banner,parent,false)
        return HomeBannerHolder(view)
    }

    override fun onBindViewHolder(holder: HomeBannerHolder, position: Int) {
        holder.bind(bannerList[position % bannerList.size])
    }

    override fun getItemCount() = Int.MAX_VALUE
}