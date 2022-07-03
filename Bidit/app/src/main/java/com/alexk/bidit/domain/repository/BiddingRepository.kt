package com.alexk.bidit.domain.repository

import com.alexk.bidit.GetBiddingInfoQuery
import com.apollographql.apollo3.api.ApolloResponse

interface BiddingRepository {
    suspend fun retrieveBiddingInfo(itemId: Int): ApolloResponse<GetBiddingInfoQuery.Data>
}