package com.example.hepsiburada.network.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*

data class Result(
        //common
        @SerializedName("trackName") val trackName: String,
        @SerializedName("artworkUrl100") val artwork100: String,
        @SerializedName("primaryGenreName") var genreName: String,
        @SerializedName("releaseDate") val releaseDate: String,
        @SerializedName("country") val country: String,
        @SerializedName("currency") val currency: String,
        @SerializedName("trackPrice") val trackPrice: Float,
        @SerializedName("genres") val genreList: ArrayList<String>?,

        //Movie and Music
        @SerializedName("trackTimeMillis") val trackTimeMillis: Int,

        //Movie
        @SerializedName("longDescription") val longDescription: String,
        @SerializedName("trackRentalPrice") val rentalPrice: Float,

        //Music
        @SerializedName("previewUrl") val previewUrl: String,
        @SerializedName("trackViewUrl") val trackViewUrl: String,

        //eBook
        @SerializedName("formattedPrice") val formattedPrice: String,
        @SerializedName("description") val description: String
) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString().toString(),
                parcel.readString().toString(),
                parcel.readString().toString(),
                parcel.readString().toString(),
                parcel.readString().toString(),
                parcel.readString().toString(),
                parcel.readFloat(),
                parcel.createStringArrayList(),
                parcel.readInt(),
                parcel.readString().toString(),
                parcel.readFloat(),
                parcel.readString().toString(),
                parcel.readString().toString(),
                parcel.readString().toString(),
                parcel.readString().toString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(trackName)
                parcel.writeString(artwork100)
                parcel.writeString(genreName)
                parcel.writeString(releaseDate)
                parcel.writeString(country)
                parcel.writeString(currency)
                parcel.writeFloat(trackPrice)
                parcel.writeStringList(genreList)
                parcel.writeInt(trackTimeMillis)
                parcel.writeString(longDescription)
                parcel.writeFloat(rentalPrice)
                parcel.writeString(previewUrl)
                parcel.writeString(trackViewUrl)
                parcel.writeString(formattedPrice)
                parcel.writeString(description)
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
