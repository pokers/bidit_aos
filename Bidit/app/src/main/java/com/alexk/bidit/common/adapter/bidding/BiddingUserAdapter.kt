package com.alexk.bidit.common.adapter.bidding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.GetBiddingInfoQuery
import com.alexk.bidit.R
import com.alexk.bidit.databinding.ItemBiddingUserListBinding

class BiddingUserAdapter() :
    ListAdapter<GetBiddingInfoQuery.GetBidding,BiddingUserAdapter.BiddingUserHolder>(BiddingUserDiffUtil) {

    class BiddingUserHolder(val binding: ItemBiddingUserListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiddingUserHolder {
        val view = DataBindingUtil.inflate<ItemBiddingUserListBinding>(LayoutInflater.from(parent.context),
            R.layout.item_bidding_user_list,parent,false)
        return BiddingUserHolder(view)
    }

    override fun onBindViewHolder(holder: BiddingUserHolder, position: Int) {
        holder.binding.biddingUserInfo = getItem(position).user
    }

    object BiddingUserDiffUtil : DiffUtil.ItemCallback<GetBiddingInfoQuery.GetBidding>() {
        override fun areItemsTheSame(
            oldItem: GetBiddingInfoQuery.GetBidding,
            newItem: GetBiddingInfoQuery.GetBidding
        ): Boolean {
            return oldItem.user?.id == newItem.user?.id
        }

        override fun areContentsTheSame(
            oldItem: GetBiddingInfoQuery.GetBidding,
            newItem: GetBiddingInfoQuery.GetBidding
        ): Boolean {
            return oldItem.user == newItem.user
        }
    }
}