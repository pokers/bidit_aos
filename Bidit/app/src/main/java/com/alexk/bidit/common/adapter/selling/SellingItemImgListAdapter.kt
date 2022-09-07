package com.alexk.bidit.common.adapter.selling

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.R
import com.alexk.bidit.databinding.ItemSellingPostMerchandiseImgListBinding
import com.alexk.bidit.domain.entity.item.ItemImgEntity


class SellingItemImgListAdapter() :
    ListAdapter<ItemImgEntity, SellingItemImgListAdapter.SellingItemImgHolder>(
        ItemImgListDiffUtil
    ) {

    var onItemClicked: ((ItemImgEntity?) -> Unit)? = null

    class SellingItemImgHolder(val binding: ItemSellingPostMerchandiseImgListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellingItemImgHolder {
        val view = DataBindingUtil.inflate<ItemSellingPostMerchandiseImgListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_selling_post_merchandise_img_list, parent, false
        )
        return SellingItemImgHolder(view)
    }

    override fun onBindViewHolder(holder: SellingItemImgHolder, position: Int) {
        with(holder.binding) {
            itemImgUrl = getItem(position)
            ivImgDelete.setOnClickListener {
                onItemClicked?.invoke(getItem(position))
            }
        }
    }

    object ItemImgListDiffUtil :
        DiffUtil.ItemCallback<ItemImgEntity>() {
        override fun areItemsTheSame(oldItem: ItemImgEntity, newItem: ItemImgEntity): Boolean {
            return oldItem.imgUrl == newItem.imgUrl
        }

        override fun areContentsTheSame(oldItem: ItemImgEntity, newItem: ItemImgEntity): Boolean {
            return oldItem == newItem
        }
    }
}
