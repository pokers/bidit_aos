package com.alexk.bidit.common.adapter.merchandise

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.R
import com.alexk.bidit.common.util.addComma
import com.alexk.bidit.data.service.response.home.HomeResponse
import com.alexk.bidit.databinding.ItemMerchandiseListBinding
import com.alexk.bidit.presentation.ui.bidding.BiddingActivity
import com.bumptech.glide.Glide


//데이터 바인딩 미적용 -> 일단 UI를 위해 임시로 해둠
/* 사용하는 UI는 반드시 submitList를 해주세요! */
class MerchandiseListAdapter(
    val context: Context,
    private val dataList: List<HomeResponse>
) :
    ListAdapter<HomeResponse, MerchandiseListAdapter.HomeMerchandiseListHolder>(
        MerchandiseListDiffUtil
    ) {

    inner class HomeMerchandiseListHolder(private val binding: ItemMerchandiseListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HomeResponse) {
            with(data) {
                Glide.with(context)
                    .load(R.drawable.ic_launcher_background)
                    .centerCrop()
                    .into(binding.ivMerchandiseImg)
                binding.tvMerchandiseClosingTime.text = time
                binding.tvMerchandiseName.text = name
                binding.tvMerchandiseCurrentPrice.text = "${addComma(price)}원"
                binding.tvNowBiddingCount.text = "100,000건"
            }
            itemView.setOnClickListener {
                context.startActivity(Intent(context, BiddingActivity::class.java))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMerchandiseListHolder {
        val view =
            DataBindingUtil.inflate<ItemMerchandiseListBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_merchandise_list_vertical,
                parent,
                false
            )
        return HomeMerchandiseListHolder(view)
    }

    override fun onBindViewHolder(holder: HomeMerchandiseListHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount() = dataList.size

    object MerchandiseListDiffUtil : DiffUtil.ItemCallback<HomeResponse>() {
        //내부 데이터가 동일한지
        /* 후에 수정해야함 */
        override fun areItemsTheSame(oldItem: HomeResponse, newItem: HomeResponse): Boolean {
            return oldItem.name == newItem.name
        }

        //두 값이 동일한 아이템인지
        override fun areContentsTheSame(oldItem: HomeResponse, newItem: HomeResponse): Boolean {
            return oldItem == newItem
        }
    }
}