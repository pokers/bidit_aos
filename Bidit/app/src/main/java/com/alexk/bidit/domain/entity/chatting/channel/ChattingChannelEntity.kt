package com.alexk.bidit.domain.entity.chatting.channel

import com.sendbird.android.channel.GroupChannel
import com.sendbird.android.channel.OpenChannel
import com.sendbird.android.message.BaseMessage

data class ChattingChannelEntity(
    val url: String,
    val lastMessage : BaseMessage?,
    val name: String,
) {
    constructor(groupChannel: GroupChannel) : this(
        groupChannel.url,
        groupChannel.lastMessage,
        groupChannel.name,
    )

    companion object {
        fun toGroupChannelInfoList(channelList: List<GroupChannel>): List<ChattingChannelEntity> {
            val results = mutableListOf<ChattingChannelEntity>()
            for (channel in channelList) {
                results.add(ChattingChannelEntity(channel))
            }
            return results
        }
    }
}
