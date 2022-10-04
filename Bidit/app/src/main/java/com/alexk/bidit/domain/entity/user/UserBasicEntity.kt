package com.alexk.bidit.domain.entity.user

import com.alexk.bidit.domain.entity.item.connection.ItemConnectionEntity
import com.alexk.bidit.domain.entity.user.alarm.UserAlarmEntity
import com.alexk.bidit.domain.entity.user.itemCount.UserItemCountEntity
import com.alexk.bidit.domain.entity.user.pushToken.UserPushTokenEntity
import com.alexk.bidit.domain.entity.user.socialLogin.UserKakaoLoginEntity
import com.alexk.bidit.type.ItemConnection
import com.alexk.bidit.type.JoinPath
import java.io.Serializable

data class UserBasicEntity(
    var id : Int? = null,
    var nickname : String? = null,
    var joinPath: JoinPath? = null,
    var email : String? = null,
    var itemCount : UserItemCountEntity? = null,
    var pushToken : UserPushTokenEntity? = null,
    var alarm : UserAlarmEntity? = null,
    var kakaoAccount : UserKakaoLoginEntity? = null,
    var itemConnection: ItemConnectionEntity? = null
):Serializable
