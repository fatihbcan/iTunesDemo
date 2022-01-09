package com.example.hepsiburada.network.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Result(
        @SerializedName("trackName") val trackName : String,
        @SerializedName("artworkUrl100") val artwork100 : String
) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString().toString(),
                parcel.readString().toString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(trackName)
                parcel.writeString(artwork100)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Result> {
                override fun createFromParcel(parcel: Parcel): Result {
                        return Result(parcel)
                }

                override fun newArray(size: Int): Array<Result?> {
                        return arrayOfNulls(size)
                }
        }
}
