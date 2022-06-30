package com.alexk.bidit.tempResponse

import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

data class HomeResponse(
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
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imgUrl)
        parcel.writeString(merchandiseName)
        parcel.writeString(endingTime)
        parcel.writeInt(currentPrice)
        parcel.writeInt(biddingPeopleCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HomeResponse> {
        override fun createFromParcel(parcel: Parcel): HomeResponse {
            return HomeResponse(parcel)
        }

        override fun newArray(size: Int): Array<HomeResponse?> {
            return arrayOfNulls(size)
        }
    }
}