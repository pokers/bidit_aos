package com.alexk.bidit.presentation.ui.bid.bidding.immediatePurchase

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.R
import com.alexk.bidit.common.util.TextUtils.addComma
import com.alexk.bidit.common.util.showLongToastMessage
import com.alexk.bidit.common.util.value.KeyConstants
import com.alexk.bidit.databinding.DialogSellingImmediatePurchaseBinding
import com.alexk.bidit.presentation.ui.chatting.chat.ChattingActivity
import com.sendbird.android.SendbirdChat
import com.sendbird.android.channel.GroupChannel
import com.sendbird.android.params.GroupChannelCreateParams

class BiddingBidImmediatePurchaseDialog(
    context: Context,
    private val price: Int?,
    private val itemRegisterUserId: Int
) : Dialog(context) {

    private lateinit var binding: DialogSellingImmediatePurchaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_selling_immediate_purchase,
            null,
            false
        )
        setContentView(binding.root)

        initPriceText()
        addButtonEvent()
    }

    private fun initPriceText() {
        binding.tvMerchandisePrice.text = addComma(price!!)
    }

    private fun createChannel() {
        val params = GroupChannelCreateParams().apply {
            //item을 등록한 user와 현재 사용중인 user간에 채팅방을 생성
            userIds = listOf(SendbirdChat.currentUser?.userId!!, itemRegisterUserId.toString())
        }
        GroupChannel.createChannel(params) createChannelLabel@{ groupChannel, e ->
            if (e != null) {
                context.showLongToastMessage("${e.message}")
                return@createChannelLabel
            }
            if (groupChannel != null) {
                val intent = Intent(context, ChattingActivity::class.java).apply {
                    putExtra(KeyConstants.INTENT_KEY_CHANNEL_URL, groupChannel.url)
                    putExtra(KeyConstants.INTENT_KEY_CHANNEL_TITLE, groupChannel.name)
                }
                context.startActivity(intent)
            }
        }
    }

    private fun addButtonEvent() {
        binding.apply {
            btnCancel.setOnClickListener {
                dismiss()
            }
            btnChat.setOnClickListener {
                createChannel()
            }
        }
    }
}