package com.alexk.bidit.presentation.ui.chatting.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.GetMyInfoQuery
import com.alexk.bidit.common.util.*
import com.alexk.bidit.databinding.ItemChattingReceiveBinding
import com.alexk.bidit.databinding.ItemChattingSendBinding
import com.sendbird.android.SendbirdChat
import com.sendbird.android.message.AdminMessage
import com.sendbird.android.message.BaseMessage
import com.sendbird.android.message.SendingStatus

class ChattingAdapter : ListAdapter<BaseMessage, RecyclerView.ViewHolder>(diffCallback) {

    private val baseMessageList = mutableListOf<BaseMessage>()
    private val pendingMessageList = mutableListOf<BaseMessage>()

    inner class GroupChatSendViewHolder(private val binding: ItemChattingSendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: BaseMessage, showTime: Boolean) {
            if (message.sendingStatus == SendingStatus.SUCCEEDED) {
                if (showTime) {
                    binding.tvDate.text = message.createdAt.toTime()
                    binding.tvDate.visibility = View.VISIBLE
                } else {
                    binding.tvDate.visibility = View.GONE
                }
            }
            binding.tvChattingMessage.text = message.message
        }
    }

    inner class GroupChatReceiverViewHolder(private val binding: ItemChattingReceiveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: BaseMessage, showTime: Boolean) {

            if (showTime) {
                binding.tvDate.text = message.createdAt.toTime()
                binding.tvDate.visibility = View.VISIBLE
            } else {
                binding.tvDate.visibility = View.GONE
            }

            binding.tvChattingMessage.text = message.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentUser = SendbirdChat.currentUser

        return if (currentUser != null) {
            if (getItem(position).sender?.userId == currentUser.userId) {
                VIEW_TYPE_SEND
            } else {
                VIEW_TYPE_RECEIVE
            }
        } else {
            VIEW_TYPE_SEND
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_SEND -> return GroupChatSendViewHolder(
                ItemChattingSendBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            VIEW_TYPE_RECEIVE -> return GroupChatReceiverViewHolder(
                ItemChattingReceiveBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            else -> return GroupChatSendViewHolder(
                ItemChattingSendBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var showTime = false

        if (currentList[position].sendingStatus == SendingStatus.SUCCEEDED) {
            showTime = true

            if (position > 0) {
                if (position < currentList.size - 1) {
                    showTime =
                        !(currentList[position].createdAt.equalTime(currentList[position + 1].createdAt))
                    if (!showTime) {
                        if (currentList[position].sender != null && currentList[position + 1].sender != null) {
                            showTime =
                                currentList[position].sender?.userId != currentList[position + 1].sender?.userId
                        }
                    }
                }
            } else {
                if (position < currentList.size - 1) {
                    showTime =
                        !(currentList[position].createdAt.equalTime(currentList[position + 1].createdAt))
                }
            }
        }

        when (holder) {
            is GroupChatReceiverViewHolder -> {
                holder.bind(getItem(position), showTime)
            }
            is GroupChatSendViewHolder -> {
                holder.bind(getItem(position), showTime)
            }
        }
    }

    fun addPendingMessages(messages: List<BaseMessage>) {
        pendingMessageList.addAll(messages)
        mergeList()
    }

    fun updateSucceedMessages(messages: List<BaseMessage>) {
        val requestIdIndexMap =
            pendingMessageList.mapIndexed { index, pendingMessage ->
                pendingMessage.requestId to index
            }.toMap()
        val messageIdIndexMap =
            baseMessageList.mapIndexed { index, baseMessage ->
                baseMessage.messageId to index
            }.toMap()
        val resultMessageList = mutableListOf<BaseMessage>().apply { addAll(pendingMessageList) }
        messages.forEach {
            val requestIndex = requestIdIndexMap[it.requestId]
            if (requestIndex != null) {
                baseMessageList.add(it)
                resultMessageList.remove(pendingMessageList[requestIndex])
            } else {
                val messageIndex = messageIdIndexMap[it.messageId]
                if (messageIndex != null) {
                    baseMessageList[messageIndex] = it
                }
            }
        }
        pendingMessageList.clear()
        pendingMessageList.addAll(resultMessageList)
        mergeList()
    }

    fun updatePendingMessages(messages: List<BaseMessage>) {
        val requestIdIndexMap =
            pendingMessageList.mapIndexed { index, pendingMessage ->
                pendingMessage.requestId to index
            }.toMap()
        messages.forEach {
            val index = requestIdIndexMap[it.requestId]
            if (index != null) {
                pendingMessageList[index] = it
            }
        }
        mergeList()
    }

    fun changeMessages(messages: List<BaseMessage>?, isPendingClear: Boolean = true) {
        baseMessageList.clear()
        if (isPendingClear) {
            pendingMessageList.clear()
        }
        if (messages != null) {
            baseMessageList.addAll(messages)
        }
        mergeList()
    }

    fun deleteMessages(messages: List<BaseMessage>) {
        val messageIdIndexMap =
            baseMessageList.mapIndexed { index, message ->
                message.messageId to index
            }.toMap()
        val resultMessageList = mutableListOf<BaseMessage>().apply { addAll(baseMessageList) }
        messages.forEach {
            val index = messageIdIndexMap[it.messageId]
            if (index != null) {
                resultMessageList.remove(baseMessageList[index])
            }
        }
        baseMessageList.clear()
        baseMessageList.addAll(resultMessageList)
        mergeList()
    }

    fun deletePendingMessages(messages: List<BaseMessage>) {
        val requestIdIndexMap =
            pendingMessageList.mapIndexed { index, pendingMessage ->
                pendingMessage.requestId to index
            }.toMap()
        val resultMessageList = mutableListOf<BaseMessage>().apply { addAll(pendingMessageList) }
        messages.forEach {
            val index = requestIdIndexMap[it.requestId]
            if (index != null) {
                resultMessageList.remove(pendingMessageList[index])
            }
        }
        pendingMessageList.clear()
        pendingMessageList.addAll(resultMessageList)
        mergeList()
    }

    fun addPreviousMessage(message: List<BaseMessage>?) {
        if (message != null) {
            baseMessageList.addAll(0, message)
            mergeList()
        }
    }

    fun addNextMessages(messages: List<BaseMessage>?) {
        if (messages != null) {
            baseMessageList.addAll(messages)
            mergeList()
        }
    }

    fun addMessages(messages: List<BaseMessage>) {
        messages.forEach {
            ListUtils.findAddMessageIndex(baseMessageList, it).apply {
                if (this > -1) {
                    baseMessageList.add(this, it)
                }
            }
        }
        mergeList()
    }

    private fun mergeList() = submitList(baseMessageList + pendingMessageList)

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<BaseMessage>() {
            override fun areItemsTheSame(oldItem: BaseMessage, newItem: BaseMessage): Boolean {
                return if (oldItem.messageId > 0 && newItem.messageId > 0) {
                    oldItem.messageId == newItem.messageId
                } else {
                    oldItem.requestId == newItem.requestId
                }
            }

            override fun areContentsTheSame(oldItem: BaseMessage, newItem: BaseMessage): Boolean {
                return oldItem.message == newItem.message
                        && oldItem.sender?.nickname == newItem.sender?.nickname
                        && oldItem.sendingStatus == newItem.sendingStatus
                        && oldItem.updatedAt == newItem.updatedAt
            }
        }
        const val VIEW_TYPE_SEND = 0
        const val VIEW_TYPE_RECEIVE = 1
    }
}