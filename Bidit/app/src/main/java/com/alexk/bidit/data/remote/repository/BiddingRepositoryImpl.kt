package com.alexk.bidit.data.remote.repository

import com.alexk.bidit.DoBidMutation
import com.alexk.bidit.GetBiddingInfoQuery
import com.alexk.bidit.GetMyBiddingInfoQuery
import com.alexk.bidit.di.ApolloClient
import com.alexk.bidit.domain.repository.BiddingRepository
import com.alexk.bidit.type.BidInput
import com.alexk.bidit.type.BiddingQueryInput
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import javax.inject.Inject

// 데이터를 받는 곳
// 앱 내에서 쓸 데이터 format을 만든다. 그거를 뷰모델로 전달



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
    ): ApolloResponse<DoBidMutation.Data> {
        return apiService.provideApolloClient().mutation(
            DoBidMutation(
                bidInfo = Optional.Present(
                    BidInput(status = Optional.Present(status), itemId = itemId, price = bidPrice)
                )
            )
        ).execute()
    }
}