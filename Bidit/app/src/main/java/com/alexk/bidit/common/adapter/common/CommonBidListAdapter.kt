package com.alexk.bidit.common.adapter.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.GetBiddingInfoQuery
import com.alexk.bidit.R
import com.alexk.bidit.databinding.ItemBidListBinding


class CommonBidListAdapter() :
    ListAdapter<GetBiddingInfoQuery.GetBidding, CommonBidListAdapter.MerchandiseListHolder>(
        BidListDiffUtil
    ) {

    var onItemClicked: ((Int?) -> Unit)? = null

    class MerchandiseListHolder(val binding: ItemBidListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MerchandiseListHolder {
        val view = DataBindingUtil.inflate<ItemBidListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_bid_list, parent, false
        )
        return MerchandiseListHolder(view)
    }

    override fun onBindViewHolder(holder: MerchandiseListHolder, position: Int) {
        with(holder.binding) {
            bidResponse = getItem(position).item
            root.setOnClickListener {
                onItemClicked?.invoke(getItem(position).item?.id)
            }
        }
    }

    object BidListDiffUtil :
        DiffUtil.ItemCallback<GetBiddingInfoQuery.GetBidding>() {
        override fun areItemsTheSame(
            oldItem: GetBiddingInfoQuery.GetBidding,
            newItem: GetBiddingInfoQuery.GetBidding
        ): Boolean {
            return oldItem.item == newItem.item
        }

        override fun areContentsTheSame(
            oldItem: GetBiddingInfoQuery.GetBidding,
            newItem: GetBiddingInfoQuery.GetBidding
        ): Boolean {
            return oldItem.item?.id == newItem.item?.id
        }
    }
}
