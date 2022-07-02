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
import com.alexk.bidit.databinding.ItemMerchandiseListBinding
import com.alexk.bidit.presentation.ui.bidding.BiddingActivity
import com.alexk.bidit.tempResponse.TempHomeResponse
import com.bumptech.glide.Glide

/* 임시 UI */

//데이터 바인딩 미적용 -> 일단 UI를 위해 임시로 해둠
/* 사용하는 UI는 반드시 submitList를 해주세요! */
class TempMerchandiseListAdapter(
    val context: Context,
    private val dataList: List<TempHomeResponse>
) :
    ListAdapter<TempHomeResponse, TempMerchandiseListAdapter.HomeMerchandiseListHolder>(
        TempMerchandiseListDiffUtil
    ) {

    class HomeMerchandiseListHolder(private val binding: ItemMerchandiseListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TempHomeResponse) {

            with(data) {
                Glide.with(binding.ivMerchandiseImg.context)
                    .load(imgUrl)
                    .into(binding.ivMerchandiseImg)
                binding.tvDeadlineTime.text = endingTime
                binding.tvCurrentPriceTitle.text = "${addComma(currentPrice)}원"
                binding.tvMerchandiseContent.text = "내용"
                binding.tvNowBiddingCount.text = "${addComma(biddingPeopleCount)}"
            }

            itemView.setOnClickListener {
                binding.ivMerchandiseImg.context.startActivity(
                    Intent(
                        binding.ivMerchandiseImg.context,
                        BiddingActivity::class.java
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMerchandiseListHolder {
        val view =
            DataBindingUtil.inflate<ItemMerchandiseListBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_merchandise_list,
                parent,
                false
            )
        return HomeMerchandiseListHolder(view)
    }

    override fun onBindViewHolder(holder: HomeMerchandiseListHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount() = dataList.size

    object TempMerchandiseListDiffUtil : DiffUtil.ItemCallback<TempHomeResponse>() {
        //ID로 변경 예정
        override fun areItemsTheSame(
            oldItem: TempHomeResponse,
            newItem: TempHomeResponse
        ): Boolean {
            return oldItem.merchandiseName == newItem.merchandiseName
        }

        override fun areContentsTheSame(
            oldItem: TempHomeResponse,
            newItem: TempHomeResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
}