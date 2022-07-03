package com.alexk.bidit.domain.repository

import com.alexk.bidit.DoBidMutation
import com.alexk.bidit.GetBiddingInfoQuery
import com.apollographql.apollo3.api.ApolloResponse

interface BiddingRepository {
    suspend fun retrieveBiddingInfo(itemId: Int): ApolloResponse<GetBiddingInfoQuery.Data>
    suspend fun doBid(itemId:Int, bidPrice:Int):ApolloResponse<DoBidMutation.Data>
}