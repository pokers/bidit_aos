package com.alexk.bidit.common.adapter.chatting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.common.adapter.chatting.callback.OnChattingChannelClickListener
import com.alexk.bidit.common.util.TextUtils
import com.alexk.bidit.common.util.toChatTime
import com.alexk.bidit.databinding.ItemChattingChannelListBinding
import com.alexk.bidit.domain.entity.chatting.channel.ChattingChannelEntity
import com.sendbird.android.channel.GroupChannel
import com.sendbird.android.channel.query.GroupChannelListQueryOrder

class ChattingChannelListAdapter(private val listener: OnChattingChannelClickListener) :
    RecyclerView.Adapter<ChattingChannelListAdapter.ChattingChannelListViewHolder>() {

    private val chattingChannelList = mutableListOf<GroupChannel>()
    private val cachedGroupChannelInfoList = mutableListOf<ChattingChannelEntity>()

    inner class ChattingChannelListViewHolder(private val binding: ItemChattingChannelListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener.onChannelClick(chattingChannelList[absoluteAdapterPosition])
            }
        }

        fun bind(channelData: GroupChannel) {
            val lastMessage = channelData.lastMessage
            binding.apply {
                tvChattingLastMessage.text = lastMessage?.message ?: ""
                tvUnreadCount.text = channelData.unreadMentionCount.toString()
                tvUserNickname.text =
                    if (channelData.name.isBlank() || channelData.name == TextUtils.CHANNEL_DEFAULT_NAME)
                        TextUtils.getGroupChannelTitle(channelData)
                    else
                        channelData.name

                tvLastChattingTime.text = (lastMessage?.createdAt ?: channelData.createdAt).toChatTime()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChattingChannelListViewHolder = ChattingChannelListViewHolder(
        ItemChattingChannelListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ChattingChannelListViewHolder, position: Int) {
        holder.bind(chattingChannelList[position])
    }

    override fun getItemCount() = chattingChannelList.size

    private class ChattingChannelDiffCallBack(
        private val oldItems: List<ChattingChannelEntity>,
        private val newItems: List<GroupChannel>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int =
            oldItems.size

        override fun getNewListSize(): Int =
            newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]

            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]
            val oldLastMessage = oldItem.lastMessage ?: return false
            val newLastMessage = newItem.lastMessage ?: return false

            return (oldItem.name == newItem.name && oldLastMessage.message == newLastMessage.message)

        }
    }

    fun addChannels(channels: List<GroupChannel>) {
        val channelUrls = channels.map { it.url }
        val newChannelList = mutableListOf<GroupChannel>().apply {
            addAll(chattingChannelList.filter { it.url !in channelUrls })
            addAll(channels)
        }
        notifyItemChanged(newChannelList)
    }

    fun updateChannels(channels: List<GroupChannel>) {
        val channelUrls = channels.map { it.url }
        val newChannelList = mutableListOf<GroupChannel>().apply {
            addAll(chattingChannelList.filter { it.url !in channelUrls })
            addAll(channels)
        }
        val groupChannelComparator = Comparator<GroupChannel> { groupChannelA, groupChannelB ->
            GroupChannel.compareTo(
                groupChannelA,
                groupChannelB,
                GroupChannelListQueryOrder.LATEST_LAST_MESSAGE,
                GroupChannelListQueryOrder.LATEST_LAST_MESSAGE.channelSortOrder
            )
        }

        newChannelList.sortWith(groupChannelComparator)
        notifyItemChanged(newChannelList)
    }

    fun deleteChannels(channelUrls: List<String>) {
        val newChannelList = mutableListOf<GroupChannel>().apply {
            addAll(chattingChannelList.filter { it.url !in channelUrls })
        }
        notifyItemChanged(newChannelList)
    }

    private fun notifyItemChanged(newChannelList: List<GroupChannel>) {
        val diffCallback = ChattingChannelDiffCallBack(cachedGroupChannelInfoList, newChannelList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        cachedGroupChannelInfoList.clear()
        cachedGroupChannelInfoList.addAll(ChattingChannelEntity.toGroupChannelInfoList(newChannelList))

        chattingChannelList.clear()
        chattingChannelList.addAll(newChannelList)
        diffResult.dispatchUpdatesTo(this)
    }


}