package com.alexk.bidit.common.adapter.home.category

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.R
import com.alexk.bidit.databinding.ItemHomeCategoryListBinding
import com.alexk.bidit.presentation.ui.category.CategoryActivity
import com.bumptech.glide.Glide

class HomeCategoryListAdapter(val context: Context, val imgList: List<Int>) :
    RecyclerView.Adapter<HomeCategoryListAdapter.HomeCategoryHolder>() {

    val categoryList = context.resources.getStringArray(R.array.category_home_item)

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
        holder.bind(categoryList[position], imgList[position])
    }

    override fun getItemCount() = categoryList.size

    inner class HomeCategoryHolder(val binding: ItemHomeCategoryListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String, img: Int) {
            binding.apply {
                Glide.with(context)
                    .load(img)
                    .into(ivCategoryImg)

                tvCategory.text = data
            }
            itemView.setOnClickListener {
                val intent = Intent(context, CategoryActivity::class.java)
                intent.putExtra("category", binding.tvCategory.text.toString())
                context.startActivity(intent)
            }
        }
    }
}