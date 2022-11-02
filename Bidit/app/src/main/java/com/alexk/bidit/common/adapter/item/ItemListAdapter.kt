package com.alexk.bidit.common.adapter.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.R
import com.alexk.bidit.databinding.ItemMerchandiseListBinding
import com.alexk.bidit.domain.entity.item.ItemBasicEntity


class ItemListAdapter :
    ListAdapter<ItemBasicEntity, ItemListAdapter.ItemListHolder>(
        MerchandiseListDiffUtil
    ) {

    var onItemClicked: ((Int?) -> Unit)? = null

    class ItemListHolder(val binding: ItemMerchandiseListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListHolder {
        val view = DataBindingUtil.inflate<ItemMerchandiseListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_merchandise_list, parent, false
        )
        return ItemListHolder(view)
    }

    override fun onBindViewHolder(holder: ItemListHolder, position: Int) {
        holder.binding.itemResponse = getItem(position)
        holder.binding.root.setOnClickListener {
            onItemClicked?.invoke(getItem(position).id)
        }
    }

    object MerchandiseListDiffUtil :
        DiffUtil.ItemCallback<ItemBasicEntity>() {
        override fun areItemsTheSame(
            oldItem: ItemBasicEntity,
            newItem: ItemBasicEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ItemBasicEntity,
            newItem: ItemBasicEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}