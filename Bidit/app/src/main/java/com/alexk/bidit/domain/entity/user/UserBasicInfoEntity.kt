package com.alexk.bidit.domain.entity.user

import com.alexk.bidit.type.JoinPath
import java.io.Serializable

data class UserBasicInfoEntity(
    val email: String?,
    val name: String?,
    val phoneNum: String?,
    val socialLogin: JoinPath?
):Serializable
