package com.alexk.bidit.common.adapter.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.R
import com.alexk.bidit.databinding.ItemMerchandiseListBinding
import com.alexk.bidit.domain.entity.item.ItemBaiscEntity


class CommonItemListAdapter() :
    ListAdapter<ItemBaiscEntity, CommonItemListAdapter.MerchandiseListHolder>(
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
        DiffUtil.ItemCallback<ItemBaiscEntity>() {
        override fun areItemsTheSame(
            oldItem: ItemBaiscEntity,
            newItem: ItemBaiscEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ItemBaiscEntity,
            newItem: ItemBaiscEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}