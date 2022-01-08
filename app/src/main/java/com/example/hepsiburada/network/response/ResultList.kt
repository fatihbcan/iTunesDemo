package com.example.hepsiburada.network.response

import com.google.gson.annotations.SerializedName

data class ResultList(
        @SerializedName("results") val resultList : List<Result>
)
