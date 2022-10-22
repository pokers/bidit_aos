package com.alexk.bidit.data.remote.repository

import com.alexk.bidit.ControlBidMutation
import com.alexk.bidit.GetBiddingInfoQuery
import com.alexk.bidit.GetMyBiddingInfoQuery
import com.alexk.bidit.common.util.value.BidStatus
import com.alexk.bidit.di.ApolloClient
import com.alexk.bidit.domain.entity.bid.BidBasicEntity
import com.alexk.bidit.domain.repository.BiddingRepository
import com.alexk.bidit.type.BidInput
import com.alexk.bidit.type.BiddingQueryInput
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
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

    override suspend fun retrieveMyBiddingInfo(): ApolloResponse<GetMyBiddingInfoQuery.Data> {
        return apiService.provideApolloClient().query(
            GetMyBiddingInfoQuery()
        ).execute()
    }

    override suspend fun controlBid(
        itemId: Int,
        bidPrice: Int,
        status: Int
    ): BidStatus {
        val bidStatus: BidStatus
        try {
            apiService.provideApolloClient().mutation(
                ControlBidMutation(
                    bidInfo = Optional.Present(
                        BidInput(
                            status = Optional.Present(status),
                            itemId = itemId,
                            price = bidPrice
                        )
                    )
                )
            ).execute().data?.bid
            bidStatus = when (status) {
                0 -> BidStatus.INVALID
                else -> BidStatus.INVALID
            }
        } catch (e: ApolloException) {
            throw ApolloException(e.message)
        }
        return bidStatus
    }
}