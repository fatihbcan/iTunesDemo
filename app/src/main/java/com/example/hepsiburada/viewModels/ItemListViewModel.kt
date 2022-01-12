package com.example.hepsiburada.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.hepsiburada.network.request.iTunesSearchKeys
import com.example.hepsiburada.repository.iTunesSearchRepository

class ItemListViewModel @ViewModelInject constructor(private val repository: iTunesSearchRepository): ViewModel() {

    // query is a live data
    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    // switchMap used to listen live data query and on every change of it, it will perform new api call
    val listItems = currentQuery.switchMap { queryPair ->
        repository.getSearchResults(queryPair.first,getCategoryName(queryPair.second)).cachedIn(viewModelScope)
    }

    // changes query instead of creating data class, pair used to pass search key and category
    fun searchItems(query: String, category: Int) {
        currentQuery.value = Pair(query, category)
    }

    // to pass category to detail fragment
    fun getCurrentQuery() : String = getCategoryName(currentQuery.value!!.second)

    // parses integer value of segmented control to api query
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
        private val DEFAULT_QUERY = Pair("",0)
    }
}