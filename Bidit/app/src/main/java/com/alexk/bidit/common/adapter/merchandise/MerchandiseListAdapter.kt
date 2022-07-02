package com.alexk.bidit.common.adapter.merchandise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.GetItemInfoQuery
import com.alexk.bidit.R
import com.alexk.bidit.databinding.ItemMerchandiseListBinding
import com.alexk.bidit.databinding.ItemMerchandiseListDatabindingBinding


/* 변경해야할 UI */

//현재는 getItemInfo, 객체 한개만 가져오므로 LIST 형태로 변경해야함
//getItemListInfo 는 구조를 좀 더 알아야 할 것 같음

class MerchandiseListAdapter : ListAdapter<GetItemInfoQuery.GetItem,MerchandiseListAdapter.MerchandiseListHolder>(MerchandiseListDiffUtil) {

    class MerchandiseListHolder(val binding : ItemMerchandiseListDatabindingBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MerchandiseListHolder {
        val view = DataBindingUtil.inflate<ItemMerchandiseListDatabindingBinding>(LayoutInflater.from(parent.context),
            R.layout.item_merchandise_list,parent,false)
        return MerchandiseListHolder(view)
    }

    override fun onBindViewHolder(holder: MerchandiseListHolder, position: Int) {
        holder.binding.merchandiseResponse = getItem(position)
    }

    object MerchandiseListDiffUtil : DiffUtil.ItemCallback<GetItemInfoQuery.GetItem>() {
        override fun areItemsTheSame(
            oldItem: GetItemInfoQuery.GetItem,
            newItem: GetItemInfoQuery.GetItem
        ): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(
            oldItem: GetItemInfoQuery.GetItem,
            newItem: GetItemInfoQuery.GetItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}