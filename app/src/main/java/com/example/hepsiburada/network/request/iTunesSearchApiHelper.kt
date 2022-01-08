package com.example.hepsiburada.network.request

import com.example.hepsiburada.network.response.ResultList
import retrofit2.Response

class iTunesSearchApiHelper(private val apiService: iTunesSearchApiService) {

    suspend fun getItemList(searchedString: String, limit : Int, category : String): Response<ResultList> = apiService.getSearchItems(searchedString, limit, category)

}