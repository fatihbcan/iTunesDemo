package com.example.hepsiburada.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.hepsiburada.data.DataPagingSource
import com.example.hepsiburada.network.request.iTunesSearchApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class iTunesSearchRepository @Inject constructor(private val iTunesSearchApiService: iTunesSearchApiService) {

    fun getSearchResults(query: String, category: String) =
        Pager(
            config = PagingConfig(
                pageSize = 1,
                maxSize = 3,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { DataPagingSource(iTunesSearchApiService, query, category) }
        ).liveData
}