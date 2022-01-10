package com.example.hepsiburada.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.hepsiburada.network.Resource
import com.example.hepsiburada.network.request.iTunesSearchKeys
import com.example.hepsiburada.network.request.iTunesSearchApiHelper
import com.example.hepsiburada.repository.ApiRepository
import com.example.hepsiburada.repository.iTunesSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemListViewModel @ViewModelInject constructor(private val repository: iTunesSearchRepository): ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val listItems = currentQuery.switchMap { queryPair ->
        repository.getSearchResults(queryPair.first,getCategoryName(queryPair.second)).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String, category: Int) {
        currentQuery.value = Pair(query, category)
    }


    private fun getCategoryName(value : Int) : String {
        return when(value){
            0 -> iTunesSearchKeys.MOVIES
            1 -> iTunesSearchKeys.MUSIC
            2 -> iTunesSearchKeys.BOOKS
            3 -> iTunesSearchKeys.PODCAST
            else -> iTunesSearchKeys.MOVIES
        }
    }

    companion object {
        private val DEFAULT_QUERY = Pair("cats",0)
    }
}