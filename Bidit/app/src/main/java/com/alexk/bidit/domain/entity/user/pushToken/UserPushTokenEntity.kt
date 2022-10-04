package com.alexk.bidit.domain.entity.user.pushToken

data class UserPushTokenEntity(
    var id: Int? = null,
    val status: Int? = null,
    val userId: Int? = null,
    val token: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)