package com.alexk.bidit.common.adapter.chatting

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.databinding.ItemChattingCreateBinding
import com.sendbird.android.User

class ChattingChannelCreateAdapter(private val listener : OnItemCheckedChangeListener):RecyclerView.Adapter<ChattingChannelCreateAdapter.ChattingChannelCreateHolder>() {

    interface OnItemCheckedChangeListener{
        fun onItemChecked(user:User, checked : Boolean)
    }

    private var users : MutableList<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChattingChannelCreateHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ChattingChannelCreateHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class ChattingChannelCreateHolder(binding : ItemChattingCreateBinding) : RecyclerView.ViewHolder(binding.root){

    }
}