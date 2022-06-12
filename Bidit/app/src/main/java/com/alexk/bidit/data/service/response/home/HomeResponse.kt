package com.alexk.bidit.data.service.response.home

import android.os.Parcel
import android.os.Parcelable

//리스폰스 구현
data class HomeResponse(
    val img: String?,
    val name: String?,
    val time: String?,
    val price: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(img)
        parcel.writeString(name)
        parcel.writeString(time)
        parcel.writeInt(price)
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