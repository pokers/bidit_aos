package com.alexk.bidit.presentation.ui.chatting.chat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.R
import com.alexk.bidit.common.util.TextUtils
import com.alexk.bidit.common.util.sharePreference.ChattingSPManager
import com.alexk.bidit.common.util.showLongToastMessage
import com.alexk.bidit.common.util.value.KeyConstants
import com.alexk.bidit.databinding.ActivityChattingBinding
import com.alexk.bidit.presentation.ui.chatting.chat.adapter.ChattingAdapter
import com.sendbird.android.SendbirdChat
import com.sendbird.android.SendbirdChat.createMessageCollection
import com.sendbird.android.channel.GroupChannel
import com.sendbird.android.collection.GroupChannelContext
import com.sendbird.android.collection.MessageCollection
import com.sendbird.android.collection.MessageCollectionInitPolicy
import com.sendbird.android.collection.MessageContext
import com.sendbird.android.exception.SendbirdException
import com.sendbird.android.handler.MessageCollectionHandler
import com.sendbird.android.handler.MessageCollectionInitHandler
import com.sendbird.android.message.BaseMessage
import com.sendbird.android.message.SendingStatus
import com.sendbird.android.params.*
import java.util.concurrent.ConcurrentHashMap

class ChattingActivity() : AppCompatActivity() {

    private lateinit var binding: ActivityChattingBinding
    private lateinit var adapter: ChattingAdapter
    private var channelUrl: String = ""
    private var channelTitle: String = ""
    private var isCollectionInitialized = false

    private var currentGroupChannel: GroupChannel? = null
    private var messageCollection: MessageCollection? = null
    private var channelTSHashMap = ConcurrentHashMap<String, Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        channelUrl = intent.getStringExtra(KeyConstants.INTENT_KEY_CHANNEL_URL) ?: ""
        channelTitle = intent.getStringExtra(KeyConstants.INTENT_KEY_CHANNEL_TITLE) ?: ""
        channelTSHashMap = ChattingSPManager.channelTSMap

        initEvent()
        initAdapter()
        getChannel(channelUrl)
    }

    private fun getChannel(channelUrl: String?) {
        if (!channelUrl.isNullOrBlank()) {
            GroupChannel.getChannel(channelUrl) getChannelLabel@{ groupChannel, e ->
                if (e != null) {
                    showLongToastMessage("${e.message}")
                    return@getChannelLabel
                }
                if (groupChannel != null) {
                    currentGroupChannel = groupChannel
                    setChannelTitle()
                    createMessageCollection(channelTSHashMap[channelUrl] ?: Long.MAX_VALUE)
                }
            }
        }
    }

    private fun setChannelTitle() {
        val currentChannel = currentGroupChannel
        if (channelTitle == TextUtils.CHANNEL_DEFAULT_NAME && currentChannel != null) {
            binding.contentToolbar.tvChattingNickname.text =
                TextUtils.getGroupChannelTitle(currentChannel)
        } else {
            binding.contentToolbar.tvChattingNickname.text = channelTitle
        }
    }

    private fun initEvent() {
        initChattingInputWatcher()
        initSendMessageEvent()
    }

    private fun initAdapter() {
        adapter = ChattingAdapter()
        binding.rcvChattingList.apply {
            itemAnimator = null
            this.adapter = adapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(-1)) {
                        loadPreviousMessageItems()
                    } else if (!recyclerView.canScrollVertically(1)) {
                        loadNextMessageItems()
                    }
                }
            })
        }
    }

    private fun loadNextMessageItems() {
        val collection = messageCollection ?: return
        if (collection.hasNext) {
            collection.loadNext { messages, e ->
                if (e != null) {
                    showLongToastMessage("${e.message}")
                    return@loadNext
                }
                adapter.addNextMessages(messages)
            }
        }
    }

    private fun loadPreviousMessageItems() {
        val collection = messageCollection ?: return
        if (collection.hasPrevious) {
            collection.loadPrevious { message, e ->
                if (e != null) {
                    showLongToastMessage("${e.message}")
                    return@loadPrevious
                }
                adapter.addPreviousMessage(message)
            }
        }
    }

    private fun initSendMessageEvent() {
        binding.btnSend.setOnClickListener {
            val message = (it as EditText).text.toString()
            sendMessage(message)
        }
    }

    private fun updateChannelView(groupChannel: GroupChannel) {
        currentGroupChannel = groupChannel
        binding.contentToolbar.tvChattingNickname.text =
            if (groupChannel.name.isBlank() || groupChannel.name == TextUtils.CHANNEL_DEFAULT_NAME)
                TextUtils.getGroupChannelTitle(groupChannel)
            else groupChannel.name
    }

    private fun initChattingInputWatcher() {
        binding.editChatting.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.btnSend.isEnabled = s.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun sendMessage(message: String) {

        val collection = messageCollection ?: return

        if (message.isNotBlank() && isCollectionInitialized) {
            val params = UserMessageCreateParams().apply {
                this.message = message.trim()
            }
            binding.editChatting.setText("")
            currentGroupChannel?.sendUserMessage(params, null)
            if (collection.hasNext) {
                createMessageCollection(Long.MAX_VALUE)
            }
        }
    }

    private fun createMessageCollection(timeStamp: Long) {
        messageCollection?.dispose()
        isCollectionInitialized = false
        val channel = currentGroupChannel
        if (channel == null) {
            finish()
            return
        }

        val messageListParams = MessageListParams().apply {
            reverse = false
            previousResultSize = 20
            nextResultSize = 20
        }

        val messageCollectionCreateParams =
            MessageCollectionCreateParams(channel, messageListParams).apply {
                startingPoint = timeStamp
                messageCollectionHandler = collectionHandler
            }
        messageCollection = SendbirdChat.createMessageCollection(messageCollectionCreateParams).apply {
            initialize(
                MessageCollectionInitPolicy.CACHE_AND_REPLACE_BY_API,
                object : MessageCollectionInitHandler {
                    override fun onCacheResult(
                        cachedList: List<BaseMessage>?,
                        e: SendbirdException?
                    ) {
                        if (e != null) {
                            showLongToastMessage("${e.message}")
                        }
                        adapter.changeMessages(cachedList)
                        adapter.addPendingMessages(this@apply.pendingMessages)
                    }

                    override fun onApiResult(
                        apiResultList: List<BaseMessage>?,
                        e: SendbirdException?
                    ) {
                        if (e != null) {
                            showLongToastMessage("${e.message}")
                        }
                        adapter.changeMessages(apiResultList, false)
                        markAsRead()
                        isCollectionInitialized = true
                    }
                }
            )
        }
    }

    private fun markAsRead() {
        currentGroupChannel?.markAsRead { e1 -> e1?.printStackTrace() }
    }

    private fun deleteMessage(baseMessage: BaseMessage) {
        currentGroupChannel?.deleteMessage(baseMessage) {
            if (it != null) {
                showLongToastMessage("${it.message}")
            }
        }
    }

    private fun updateChannelView(name: String, channel: GroupChannel) {
        if (name.isBlank()) {
            showLongToastMessage("updateChannelView : name.isBlanck")
            return
        }
        if (channel.name != name) {
            val params = GroupChannelUpdateParams()
                .apply { this.name = name }
            channel.updateChannel(
                params
            ) { _, e ->
                if (e != null) {
                    showLongToastMessage("${e.message}")
                }
            }
        }
    }

    private fun deleteChannel() {
        currentGroupChannel?.delete {
            if (it != null) {
                showLongToastMessage("${it.message}")
                return@delete
            }
            finish()
        }
    }

    private fun leaveChannel() {
        currentGroupChannel?.leave {
            if (it != null) {
                showLongToastMessage("${it.message}")
            }
            finish()
        }
    }

    private fun updateMessage(msg: String, baseMessage: BaseMessage) {
        if (msg.isBlank()) {
            showLongToastMessage("updateMessage : msg.isBlanck")
            return
        }
        val params = UserMessageUpdateParams().apply {
            message = msg
        }
        currentGroupChannel?.updateUserMessage(
            baseMessage.messageId, params
        ) { _, e ->
            if (e != null) {
                showLongToastMessage("${e.message}")
            }
        }
    }

    override fun onPause() {
        val lastMessage = adapter.currentList.lastOrNull()
        if(lastMessage != null && channelUrl.isNotBlank()){
            channelTSHashMap[channelUrl] = lastMessage.createdAt
            ChattingSPManager.channelTSMap = channelTSHashMap
        }
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        messageCollection?.dispose()
        SendbirdChat.autoBackgroundDetection = true
    }

    private val collectionHandler = object : MessageCollectionHandler {
        override fun onMessagesAdded(
            context: MessageContext,
            channel: GroupChannel,
            messages: List<BaseMessage>
        ) {
            when (context.messagesSendingStatus) {
                SendingStatus.SUCCEEDED -> {
                    adapter.addMessages(messages)
                    markAsRead()
                }

                SendingStatus.PENDING -> adapter.addPendingMessages(messages)

                else -> {
                }
            }
        }

        override fun onMessagesUpdated(
            context: MessageContext,
            channel: GroupChannel,
            messages: List<BaseMessage>
        ) {
            when (context.messagesSendingStatus) {
                SendingStatus.SUCCEEDED -> adapter.updateSucceedMessages(messages)

                SendingStatus.PENDING -> adapter.updatePendingMessages(messages)

                SendingStatus.FAILED -> adapter.updatePendingMessages(messages)

                SendingStatus.CANCELED -> adapter.deletePendingMessages(messages)

                else -> {
                }
            }
        }

        override fun onMessagesDeleted(
            context: MessageContext,
            channel: GroupChannel,
            messages: List<BaseMessage>
        ) {
            when (context.messagesSendingStatus) {
                SendingStatus.SUCCEEDED -> adapter.deleteMessages(messages)

                SendingStatus.FAILED -> adapter.deletePendingMessages(messages)

                else -> {
                }
            }
        }

        override fun onChannelUpdated(context: GroupChannelContext, channel: GroupChannel) {
            updateChannelView(channel)
        }

        override fun onChannelDeleted(context: GroupChannelContext, channelUrl: String) {

        }

        override fun onHugeGapDetected() {
            val collection = messageCollection
            if (collection == null) {
                finish()
                return
            }
            val startingPoint = collection.startingPoint
            collection.dispose()
            val position: Int =
                (binding.rcvChattingList.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            if (position >= 0) {
                val message: BaseMessage = adapter.currentList[position]
                createMessageCollection(message.createdAt)
            } else {
                createMessageCollection(startingPoint)
            }
        }

    }
}