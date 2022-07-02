package com.alexk.bidit.tempResponse

import android.os.Parcel
import android.os.Parcelable

data class TempHomeResponse(
    val imgUrl : String?,
    val merchandiseName : String?,
    val endingTime : String?,
    val biddingPeopleCount : Int,
    val currentPrice : Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imgUrl)
        parcel.writeString(merchandiseName)
        parcel.writeString(endingTime)
        parcel.writeInt(biddingPeopleCount)
        parcel.writeInt(currentPrice)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TempHomeResponse> {
        override fun createFromParcel(parcel: Parcel): TempHomeResponse {
            return TempHomeResponse(parcel)
        }

        override fun newArray(size: Int): Array<TempHomeResponse?> {
            return arrayOfNulls(size)
        }
    }
}