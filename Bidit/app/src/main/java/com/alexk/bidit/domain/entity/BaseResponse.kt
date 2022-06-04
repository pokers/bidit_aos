package com.alexk.bidit.domain.entity

import com.google.gson.annotations.SerializedName

//공용으로 response 되는 데이터
open class BaseResponse(
    @SerializedName("data")val data : String
)
