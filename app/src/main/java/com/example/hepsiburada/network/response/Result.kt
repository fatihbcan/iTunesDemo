package com.example.hepsiburada.network.response

import com.google.gson.annotations.SerializedName

data class Result(
        @SerializedName("trackName") val trackName : String,
        @SerializedName("artworkUrl100") val artwork100 : String
)
