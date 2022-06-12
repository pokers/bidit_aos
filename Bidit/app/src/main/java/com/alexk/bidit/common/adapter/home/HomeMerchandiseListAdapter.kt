package com.alexk.bidit.common.adapter.home

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alexk.bidit.R
import com.alexk.bidit.common.util.addComma
import com.alexk.bidit.data.service.response.home.HomeResponse
import com.alexk.bidit.databinding.ItemMerchandiseListBinding
import com.bumptech.glide.Glide


//데이터 바인딩 미적용 -> 일단 UI를 위해 임시로 해둠
class HomeMerchandiseListAdapter(
    val context: Context,
    private val dataList: List<HomeResponse>
) :
    RecyclerView.Adapter<HomeMerchandiseListAdapter.HomeMerchandiseListHolder>() {

    inner class HomeMerchandiseListHolder(private val binding: ItemMerchandiseListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HomeResponse) {
            with(data) {
//                Glide.with(context)
//                    .load(img)
//                    .centerCrop()
//                    .into(binding.ivMerchandiseImg)
                binding.tvMerchandiseClosingTime.text = time
                binding.tvMerchandiseName.text = name
                binding.tvMerchandiseCurrentPrice.text = "${addComma(price)}원"
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

}