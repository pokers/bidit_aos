package com.alexk.bidit.common.adapter.search

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.R
import com.alexk.bidit.data.sharedPreference.SearchKeywordManager
import com.alexk.bidit.databinding.ItemRecentSearchKeywordListBinding

class SearchKeywordListAdapter(
    val context: Context,
    var keywordList: ArrayList<String>,
    val onClickDeleteKeyword: (keyword: String) -> Unit,
    val onClickItem: (keyword: String) -> Unit
) :
    RecyclerView.Adapter<SearchKeywordListAdapter.SearchKeywordListHolder>() {


    inner class SearchKeywordListHolder(val binding: ItemRecentSearchKeywordListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(keyword: String) {
            binding.tvSearchKeyword.text = keyword
            binding.btnDelete.setOnClickListener {
                //해당 키워드를 삭제
                keywordList.remove(keyword)
                //삭제된 리스트를 적용
                SearchKeywordManager(context).removeKeyword(keywordList)
                onClickDeleteKeyword.invoke(keyword)
            }
            binding.tvSearchKeyword.setOnClickListener {
                onClickItem.invoke(keyword)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchKeywordListHolder {
        val view = DataBindingUtil.inflate<ItemRecentSearchKeywordListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_recent_search_keyword_list, parent, false
        )
        return SearchKeywordListHolder(view)
    }

    override fun onBindViewHolder(holder: SearchKeywordListHolder, position: Int) {
        holder.bind(keywordList[position])
    }

    override fun getItemCount() = keywordList.size


    @SuppressLint("NotifyDataSetChanged")
    fun setKeyword(newKeywordList: ArrayList<String>) {
        keywordList = newKeywordList
        notifyDataSetChanged()
    }
}
