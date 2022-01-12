package com.example.hepsiburada.network.request

import retrofit2.http.GET
import retrofit2.http.Query

interface iTunesSearchApiService {

    companion object {
        const val BASE_URL = "https://itunes.apple.com/"
    }

    @GET("search")
    suspend fun getSearchItems(
        @Query("term") searchedString: String, //searched text
        @Query("offset") offSet : Int, // to skip some data for paging
        @Query("limit") limit: Int, // response item limit
        @Query("entity") entity: String // category
    ): iTunesApiResponse
}