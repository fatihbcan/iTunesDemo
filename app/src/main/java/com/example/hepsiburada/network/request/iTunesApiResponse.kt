package com.example.hepsiburada.network.request

import com.example.hepsiburada.data.ItemListData
import com.google.gson.annotations.SerializedName

data class iTunesApiResponse(
    @SerializedName("results") val results : List<ItemListData>
)