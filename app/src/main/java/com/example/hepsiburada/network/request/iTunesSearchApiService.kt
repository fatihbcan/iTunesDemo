package com.example.hepsiburada.network.request

import com.example.hepsiburada.network.response.ResultList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface iTunesSearchApiService {

    @GET("search")
    suspend fun getSearchItems(
        @Query("term") searchedString: String,
        @Query("limit") limit: Int,
        @Query("entity") entity: String
    ): Response<ResultList>
}