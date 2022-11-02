package com.alexk.bidit.common.adapter.chatting.callback

import com.sendbird.android.channel.GroupChannel
import com.sendbird.android.channel.OpenChannel

interface OnChattingChannelClickListener {
    fun onChannelClick(groupChannel: GroupChannel)
}