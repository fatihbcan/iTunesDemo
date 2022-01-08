package com.example.hepsiburada.repository

import com.example.hepsiburada.network.request.iTunesSearchApiHelper
import com.example.hepsiburada.network.response.ResultList
import retrofit2.Response

class ApiRepository(private val apiHelper: iTunesSearchApiHelper) {

    suspend fun getItemList(searchedString: String, limit : Int, searchItemName : String): Response<ResultList> = apiHelper.getItemList(searchedString, limit, searchItemName)
}