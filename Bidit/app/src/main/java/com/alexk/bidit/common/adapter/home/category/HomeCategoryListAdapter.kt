package com.alexk.bidit.common.adapter.home.category

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.R
import com.alexk.bidit.data.service.response.home.HomeCategoryResponse
import com.alexk.bidit.databinding.ItemHomeCategoryListBinding
import com.bumptech.glide.Glide

class HomeCategoryListAdapter(val context: Context, val list: List<HomeCategoryResponse>) :
    RecyclerView.Adapter<HomeCategoryListAdapter.HomeCategoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoryHolder {
        val view = DataBindingUtil.inflate<ItemHomeCategoryListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_home_category_list,
            parent,
            false
        )
        return HomeCategoryHolder(view)
    }

    override fun onBindViewHolder(holder: HomeCategoryHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    inner class HomeCategoryHolder(val binding: ItemHomeCategoryListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HomeCategoryResponse) {
            binding.apply {
                Glide.with(context)
                    .load(R.drawable.ic_launcher_background)
                    .centerCrop()
                    .into(ivCategoryImg)

                tvCategory.text = data.title
            }
        }
    }
}