package com.alexk.bidit.data.repository

import com.alexk.bidit.DoBidMutation
import com.alexk.bidit.GetBiddingInfoQuery
import com.alexk.bidit.di.ApolloClient
import com.alexk.bidit.domain.repository.BiddingRepository
import com.alexk.bidit.type.BidInput
import com.alexk.bidit.type.BiddingQueryInput
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import javax.inject.Inject

class BiddingRepositoryImpl @Inject constructor(private val apiService: ApolloClient) :
    BiddingRepository {
    override suspend fun retrieveBiddingInfo(itemId: Int): ApolloResponse<GetBiddingInfoQuery.Data> {
        return apiService.provideApolloClient().query(
            GetBiddingInfoQuery(
                biddingQuery = Optional.Present(
                    BiddingQueryInput(itemId = Optional.Present(itemId))
                )
            )
        ).execute()
    }

    override suspend fun doBid(itemId: Int, bidPrice: Int): ApolloResponse<DoBidMutation.Data> {
        return apiService.provideApolloClient().mutation(
            DoBidMutation(
                bidInfo = Optional.Present(
                    BidInput(itemId = itemId, price = bidPrice)
                )
            )
        ).execute()
    }
}