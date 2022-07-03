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
import com.alexk.bidit.databinding.ItemMerchandiseListBinding
import com.bumptech.glide.Glide


class MerchandiseListAdapter() :
    ListAdapter<GetItemListQuery.Edge, MerchandiseListAdapter.MerchandiseListHolder>(
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
        with(holder.binding) {
            merchandiseResponse = getItem(position)
            root.setOnClickListener {
                onItemClicked?.invoke(getItem(position).node?.id)
            }
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