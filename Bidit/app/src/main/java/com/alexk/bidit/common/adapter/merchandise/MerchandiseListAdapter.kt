package com.alexk.bidit.common.adapter.merchandise

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.GetItemListQuery
import com.alexk.bidit.R
import com.alexk.bidit.common.util.addComma
import com.alexk.bidit.databinding.ItemMerchandiseEndingSoonListBinding
import com.bumptech.glide.Glide


class MerchandiseListAdapter :
    ListAdapter<GetItemListQuery.Edge, MerchandiseListAdapter.MerchandiseListHolder>(
        MerchandiseListDiffUtil
    ) {

    class MerchandiseListHolder(val binding: ItemMerchandiseEndingSoonListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MerchandiseListHolder {
        val view = DataBindingUtil.inflate<ItemMerchandiseEndingSoonListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_merchandise_ending_soon_list, parent, false
        )
        return MerchandiseListHolder(view)
    }

    override fun onBindViewHolder(holder: MerchandiseListHolder, position: Int) {
        with(holder.binding) {
            merchandiseResponse = getItem(position)
            root.setOnClickListener {

            }
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun ImageView.loadImage(imageUrlList: List<GetItemListQuery.Image?>?) {
            if (!imageUrlList.isNullOrEmpty()) {
                Glide.with(this.context)
                    .load(imageUrlList[0]?.url)
                    .into(this)
            }
        }

        @JvmStatic
        @BindingAdapter("price")
        fun TextView.changePriceType(price: Int?) {
            price?.let { this.text = "${addComma(price)}원" }
        }

        @JvmStatic
        @BindingAdapter("number")
        fun TextView.changeNumberType(number: Int?) {
            number?.let { this.text = addComma(number) }
        }

        @JvmStatic
        @BindingAdapter("time")
        fun TextView.changeDateType(date: String?) {
            //텍스트 날짜 형식으로 변환 필요
            date?.let { this.text = date }
        }
    }

    object MerchandiseListDiffUtil :
        DiffUtil.ItemCallback<GetItemListQuery.Edge>() {
        override fun areItemsTheSame(
            oldItem: GetItemListQuery.Edge,
            newItem: GetItemListQuery.Edge
        ): Boolean {
            return oldItem.node?.id == newItem.node?.id
        }

        override fun areContentsTheSame(
            oldItem: GetItemListQuery.Edge,
            newItem: GetItemListQuery.Edge
        ): Boolean {
            return oldItem.node == newItem.node
        }
    }
}