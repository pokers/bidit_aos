package com.alexk.bidit.tempResponse

data class BiddingUserResponse(
    val userName: String,
    val endingTime: String,
    val currentPrice: Int,
    val addedPrice: Int,
    val imgUrl : String
)