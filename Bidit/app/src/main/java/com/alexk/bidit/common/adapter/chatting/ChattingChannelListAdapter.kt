package com.alexk.bidit.common.adapter.chatting

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.R
import com.alexk.bidit.databinding.ItemChannelChooserBinding
import com.sendbird.android.AdminMessage
import com.sendbird.android.FileMessage
import com.sendbird.android.GroupChannel
import com.sendbird.android.UserMessage

class ChattingChannelListAdapter(private val listener: OnChannelClickedListener) :
    RecyclerView.Adapter<ChattingChannelListAdapter.ChannelHolder>() {

    interface OnChannelClickedListener{
        fun onItemClicked(channel : GroupChannel)
    }

    private var channels : MutableList<GroupChannel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelHolder {
        val view = DataBindingUtil.inflate<ItemChannelChooserBinding>(LayoutInflater.from(parent.context),
            R.layout.item_channel_chooser,parent,false)
        return ChannelHolder(view)
    }

    override fun onBindViewHolder(holder: ChannelHolder, position: Int) {
        holder.bind(channels[position],listener)
    }

    override fun getItemCount() = channels.size

    @SuppressLint("NotifyDataSetChanged")
    fun addChannels(channels:MutableList<GroupChannel>){
        this.channels = channels
        notifyDataSetChanged()
    }

    class ChannelHolder(binding: ItemChannelChooserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val channelName = binding.tvChannelName
        val channelRecentMessage = binding.tvChannelRecent
        val channelMemberCount = binding.tvChannelMemberCount

        fun bind(groupChannel: GroupChannel, listener: OnChannelClickedListener) {
            val lastMessage = groupChannel.lastMessage

            if (lastMessage != null){
                when(lastMessage){
                    is UserMessage -> channelRecentMessage.text = lastMessage.message
                    is AdminMessage -> channelMemberCount.text = lastMessage.message
                    else -> {
                        val fileMessage = lastMessage as FileMessage
                        val sender = fileMessage.sender.nickname

                        channelRecentMessage.text = sender
                    }
                }
            }
            itemView.setOnClickListener {
                listener.onItemClicked(channel = groupChannel)
            }

            channelName.text = groupChannel.members[0].nickname
            channelMemberCount.text = groupChannel.memberCount.toString()
        }
    }
}