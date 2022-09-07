package com.alexk.bidit.common.adapter.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.R
import com.alexk.bidit.databinding.ItemMerchandiseListBinding
import com.alexk.bidit.domain.entity.item.ItemEntity


class CommonItemListAdapter() :
    ListAdapter<ItemEntity, CommonItemListAdapter.MerchandiseListHolder>(
        MerchandiseListDiffUtil
    ) {

    var onItemClicked: ((Int?) -> Unit)? = null

    class MerchandiseListHolder(val binding: ItemMerchandiseListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MerchandiseListHolder {
        val view = DataBindingUtil.inflate<ItemMerchandiseListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_merchandise_list, parent, false
        )
        return MerchandiseListHolder(view)
    }

    override fun onBindViewHolder(holder: MerchandiseListHolder, position: Int) {
        holder.binding.itemResponse = getItem(position)
        holder.binding.root.setOnClickListener {
            onItemClicked?.invoke(getItem(position).id)
        }
    }

    object MerchandiseListDiffUtil :
        DiffUtil.ItemCallback<ItemEntity>() {
        override fun areItemsTheSame(
            oldItem: ItemEntity,
            newItem: ItemEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ItemEntity,
            newItem: ItemEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}