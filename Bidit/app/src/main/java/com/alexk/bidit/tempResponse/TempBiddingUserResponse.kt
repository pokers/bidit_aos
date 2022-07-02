package com.alexk.bidit.tempResponse

data class TempBiddingUserResponse(
    val userName: String,
    val endingTime: String,
    val currentPrice: Int,
    val addedPrice: Int,
    val imgUrl : String
)