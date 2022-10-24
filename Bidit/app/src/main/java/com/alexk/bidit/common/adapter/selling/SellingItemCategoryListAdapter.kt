package com.alexk.bidit.common.adapter.selling

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.GetBiddingInfoQuery
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.selling.callback.SellingItemCategoryClickListener
import com.alexk.bidit.common.util.setTextColorWithResourceCompat
import com.alexk.bidit.databinding.ItemSellingCategoryListBinding

class SellingItemCategoryListAdapter() :
    RecyclerView.Adapter<SellingItemCategoryListAdapter.SellingItemCategoryListHolder>() {

    private val categoryList = mutableListOf<String>()
    private var selectIndex = -1
    private lateinit var categoryClickListener: SellingItemCategoryClickListener

    inner class SellingItemCategoryListHolder(val binding: ItemSellingCategoryListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String) {
            if (absoluteAdapterPosition + 1 == selectIndex) {
                binding.tvCategory.setTextColorWithResourceCompat(R.color.persian_blue)
            }
            binding.tvCategory.text = category
            itemView.setOnClickListener {
                categoryClickListener.onCategoryClick(absoluteAdapterPosition + 1)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SellingItemCategoryListHolder {
        val view = ItemSellingCategoryListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SellingItemCategoryListHolder(view)
    }

    override fun onBindViewHolder(holder: SellingItemCategoryListHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount() = categoryList.size

    fun addCategoryItem(list: List<String>) {
        categoryList.addAll(list)
        //first index value is digital..
        categoryList.removeAt(0)
    }

    fun selectedIndex(index: Int) {
        if (index != -1) this.selectIndex = index
    }

    fun setItemCategoryClickListener(categoryClickListener: SellingItemCategoryClickListener) {
        this.categoryClickListener = categoryClickListener
    }

    companion object {
        private const val TAG = "SellingItemCategoryList"
    }
}