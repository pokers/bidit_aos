package com.alexk.bidit.presentation.ui.chatting.channel

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.chatting.ChattingChannelListAdapter
import com.alexk.bidit.common.adapter.chatting.callback.OnChattingChannelClickListener
import com.alexk.bidit.common.util.showLongToastMessage
import com.alexk.bidit.common.util.value.KeyConstants.INTENT_KEY_CHANNEL_TITLE
import com.alexk.bidit.common.util.value.KeyConstants.INTENT_KEY_CHANNEL_URL
import com.alexk.bidit.databinding.FragmentChattingChannelListBinding
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.ui.chatting.chat.ChattingActivity
import com.sendbird.android.SendbirdChat
import com.sendbird.android.channel.GroupChannel
import com.sendbird.android.channel.OpenChannel.Companion.createOpenChannelListQuery
import com.sendbird.android.channel.query.GroupChannelListQueryOrder
import com.sendbird.android.channel.query.MyMemberStateFilter
import com.sendbird.android.channel.query.OpenChannelListQuery
import com.sendbird.android.collection.GroupChannelCollection
import com.sendbird.android.collection.GroupChannelContext
import com.sendbird.android.handler.GroupChannelCollectionHandler
import com.sendbird.android.params.GroupChannelCollectionCreateParams
import com.sendbird.android.params.GroupChannelListQueryParams
import com.sendbird.android.params.OpenChannelListQueryParams

class ChattingChannelListFragment :
    BaseFragment<FragmentChattingChannelListBinding>(R.layout.fragment_chatting_channel_list) {

    private lateinit var openChannelListQuery: OpenChannelListQuery
    private lateinit var chattingChannelAdapter: ChattingChannelListAdapter
    private lateinit var groupChannelCollection: GroupChannelCollection

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChattingChannelList()
    }

    private fun initChattingChannelList() {
        openChannelListQuery = createOpenChannelListQuery(OpenChannelListQueryParams())

        initChannelList()
        initChannelSetting()
    }

    private fun initListClickListener() = object : OnChattingChannelClickListener {
        override fun onChannelClick(groupChannel: GroupChannel) {
            val intent = Intent(context, ChattingActivity::class.java).apply {
                putExtra(INTENT_KEY_CHANNEL_URL, groupChannel.url)
                putExtra(INTENT_KEY_CHANNEL_TITLE, groupChannel.name)
            }
            startActivity(intent)
        }
    }

    private fun initChannelList() {
        chattingChannelAdapter = ChattingChannelListAdapter(initListClickListener())
        binding.rvChattingList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = chattingChannelAdapter
        }
    }

    private fun initChannelSetting() {
        val listQuery = GroupChannel.createMyGroupChannelListQuery(
            (GroupChannelListQueryParams(
                order = GroupChannelListQueryOrder.LATEST_LAST_MESSAGE,
                myMemberStateFilter = MyMemberStateFilter.ALL
            ))
        )
        val params = GroupChannelCollectionCreateParams(listQuery)
        groupChannelCollection = SendbirdChat.createGroupChannelCollection(params).apply {
            groupChannelCollectionHandler = (object : GroupChannelCollectionHandler {
                override fun onChannelsAdded(
                    context: GroupChannelContext,
                    channels: List<GroupChannel>
                ) {
                    chattingChannelAdapter.updateChannels(channels)
                }

                override fun onChannelsDeleted(
                    context: GroupChannelContext,
                    deletedChannelUrls: List<String>
                ) {
                    chattingChannelAdapter.deleteChannels(deletedChannelUrls)
                }

                override fun onChannelsUpdated(
                    context: GroupChannelContext,
                    channels: List<GroupChannel>
                ) {
                    chattingChannelAdapter.updateChannels(channels)
                }
            })
        }
        getChannelList()
    }

    private fun getChannelList(isRefreshing: Boolean = false) {
        val collection = groupChannelCollection
        if (collection.hasMore) {
            collection.loadMore loadMoreLabel@{ channelList, e ->
                if (e != null || channelList == null) {
                    context?.showLongToastMessage("${e?.message}")
                    return@loadMoreLabel
                }
                if (channelList.isNotEmpty()) {
                    if (isRefreshing) {
                        chattingChannelAdapter.addChannels(emptyList())
                    }
                    chattingChannelAdapter.addChannels(channelList)
                }else{
                    binding.lyNoList.apply {
                        root.visibility = View.VISIBLE
                        tvNoKeyword.text = "채팅이 존재하지 않습니다."
                    }
                }
            }
        }
    }
}