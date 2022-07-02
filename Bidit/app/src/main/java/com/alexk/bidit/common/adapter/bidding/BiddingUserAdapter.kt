package com.alexk.bidit.common.adapter.bidding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.R
import com.alexk.bidit.common.util.addComma
import com.alexk.bidit.databinding.ItemBiddingListBinding
import com.alexk.bidit.tempResponse.TempBiddingUserResponse
import com.bumptech.glide.Glide

class BiddingUserAdapter(val context: Context, val userListTemp: List<TempBiddingUserResponse>) :
    RecyclerView.Adapter<BiddingUserAdapter.BiddingUserHolder>() {
    inner class BiddingUserHolder(val binding: ItemBiddingListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TempBiddingUserResponse) {
            binding.apply {
                tvBiddingPrice.text = "+${addComma(data.addedPrice)}원"
                tvBiddingTotalPrice.text = "${addComma(data.currentPrice)}원"
                tvBiddingTime.text = data.endingTime

                Glide.with(ivUserImg.context)
                    .load(data.imgUrl)
                    .into(ivUserImg)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiddingUserHolder {
        val view = DataBindingUtil.inflate<ItemBiddingListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_bidding_list, parent, false
        )
        return BiddingUserHolder(view)
    }

    override fun onBindViewHolder(holder: BiddingUserHolder, position: Int) {
        holder.bind(userListTemp[position])
    }

    override fun getItemCount() = userListTemp.size
}