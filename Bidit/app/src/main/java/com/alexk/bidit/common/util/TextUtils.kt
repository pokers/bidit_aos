package com.alexk.bidit.common.util

import com.alexk.bidit.GlobalApplication
import com.sendbird.android.channel.GroupChannel

object TextUtils {

    const val CHANNEL_DEFAULT_NAME = "Group Channel"

    fun addComma(number: Int): String = if (number >= 0) {
        "%,d".format(number)
    } else {
        "- "
    }

    fun getGroupChannelTitle(channel: GroupChannel): String {
        return when {
            channel.members.size < 2 -> "No Members"
            else -> {

                //상대방의 이름만 나오도록 설정
                channel.members.joinToString(
                    limit = 10,
                    separator = ""
                ) {
                    if (it.nickname != GlobalApplication.userNickname) it.nickname
                    else ""
                }
            }
        }
    }
}