package com.alexk.bidit.presentation.ui.chatting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.chatting.ChattingChannelListAdapter
import com.alexk.bidit.common.util.EXTRA_CHANNEL_URL
import com.alexk.bidit.databinding.FragmentChattingChannelBinding
import com.alexk.bidit.presentation.base.BaseFragment
import com.sendbird.android.GroupChannel

class ChattingChannelListFragment :
    BaseFragment<FragmentChattingChannelBinding>(R.layout.fragment_chatting_channel),
    ChattingChannelListAdapter.OnChannelClickedListener {

    private lateinit var chattingChannelListAdapter: ChattingChannelListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChannelList()
    }

    private fun init() {
        binding.apply {
            rvGroupChannels.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvGroupChannels.adapter = chattingChannelListAdapter
        }
    }

    private fun initChannelList() {
        chattingChannelListAdapter = ChattingChannelListAdapter(this)
        binding.rvGroupChannels.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvGroupChannels.adapter = chattingChannelListAdapter

        addChannels()
    }

    private fun addChannels(){
        val channelList = GroupChannel.createMyGroupChannelListQuery()
        channelList.limit = 100
        channelList.next{list , e ->
            if(e != null){
                Log.e(TAG, e.message.toString())
            }
            chattingChannelListAdapter.addChannels(channels = list)
        }
    }

    private fun initEvent() {
        binding.btnGroupChannelCreate.setOnClickListener {
            startActivity(Intent(requireContext(), ChattingCreateActivity::class.java))
        }
    }

    override fun onItemClicked(channel: GroupChannel) {
        val intent = Intent(requireContext(), ChattingActivity::class.java)
        intent.putExtra(EXTRA_CHANNEL_URL, channel.url)
        startActivity(intent)
    }


    companion object {
        private const val TAG = "ChattingChannelListFragment..."
    }
}