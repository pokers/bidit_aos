package com.alexk.bidit.common.adapter.selling

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.GetBiddingInfoQuery
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.common.CommonBidListAdapter
import com.alexk.bidit.databinding.ItemBidListBinding
import com.alexk.bidit.databinding.ItemSellingPostMerchandiseImgListBinding
import com.alexk.bidit.domain.entity.merchandise.MerchandiseImgEntity


class SellingItemImgListAdapter() :
    ListAdapter<MerchandiseImgEntity, SellingItemImgListAdapter.SellingItemImgHolder>(
        ItemImgListDiffUtil
    ) {

    var onItemClicked: ((Int?) -> Unit)? = null

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
                onItemClicked?.invoke(position)
            }
        }
    }

    object ItemImgListDiffUtil :
        DiffUtil.ItemCallback<MerchandiseImgEntity>() {
        override fun areItemsTheSame(oldItem: MerchandiseImgEntity, newItem: MerchandiseImgEntity): Boolean {
            return oldItem.imgUrl == newItem.imgUrl
        }

        override fun areContentsTheSame(oldItem: MerchandiseImgEntity, newItem: MerchandiseImgEntity): Boolean {
            return oldItem == newItem
        }
    }
}
