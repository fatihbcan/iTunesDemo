package com.example.hepsiburada.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.example.hepsiburada.network.request.iTunesApiResponse
import com.example.hepsiburada.network.request.iTunesSearchApiService
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1
private const val LIMIT = 20


class DataPagingSource(
    private val itunesSearchApiService: iTunesSearchApiService,
    private val query: String,
    private val category: String
) : PagingSource<Int, ItemListData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemListData> {
        val position = params.key ?: STARTING_PAGE_INDEX // page position default 1
        val offset = (position - 1) * LIMIT // to skip some data to prevent calling same ones for every page

        return try {
            val response = itunesSearchApiService.getSearchItems(query, offset, LIMIT, category) // api call
            val resultList = response.results // get api call results

            LoadResult.Page(
                data = resultList,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (resultList.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ItemListData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}