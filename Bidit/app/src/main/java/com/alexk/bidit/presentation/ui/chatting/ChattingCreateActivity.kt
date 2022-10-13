package com.alexk.bidit.presentation.ui.chatting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.chatting.ChattingChannelCreateAdapter
import com.alexk.bidit.common.util.EXTRA_CHANNEL_URL
import com.alexk.bidit.databinding.ActivityChattingCreateBinding
import com.sendbird.android.GroupChannel
import com.sendbird.android.GroupChannelParams
import com.sendbird.android.SendBird

class ChattingCreateActivity : AppCompatActivity() {

    private lateinit var selectedUsers: ArrayList<String>
    private lateinit var adapter: ChattingChannelCreateAdapter
    private lateinit var binding: ActivityChattingCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chatting_create)
    }

    private fun initChattingCreateAdapter() {

    }

    private fun createChannel(users: MutableList<String>) {
        val params = GroupChannelParams()
        val operatorId = ArrayList<String>()
        operatorId.add(SendBird.getCurrentUser().userId)

        params.addUserIds(users)
        params.setOperatorUserIds(operatorId)

        GroupChannel.createChannel(params) { groupChannel, e ->
            if (e != null){
                Log.e(TAG,e.message.toString())
            }else{
                val intent = Intent(this, ChannelActivity::class.java)
                intent.putExtra(EXTRA_CHANNEL_URL, groupChannel.url)
                startActivity(intent)
            }
        }
    }

    private fun loadUsers() {
        val userListQuery = SendBird.createApplicationUserListQuery()

        userListQuery.next() { _, e ->
            if (e != null) {
                Log.e(TAG, e.message.toString())
            } else {

            }
        }
    }

    companion object {
        private const val TAG = "ChattingCreateActivity";
    }
}