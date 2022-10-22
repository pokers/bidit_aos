package com.alexk.bidit.domain.entity.user.socialLogin

data class UserKakaoLoginEntity(
    var name: String? = null,
    var email: String? = null,
    var phoneNumber: String? = null,
    var nickName: String? = null,
    var profileImageUrl: String? = null
)