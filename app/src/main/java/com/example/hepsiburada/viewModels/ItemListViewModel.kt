package com.example.hepsiburada.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.hepsiburada.network.Resource
import com.example.hepsiburada.network.request.iTunesSearchKeys
import com.example.hepsiburada.network.request.iTunesSearchApiHelper
import com.example.hepsiburada.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemListViewModel (private val apiRepository: ApiRepository): ViewModel() {

    private var searchedTextAndCategory : Pair<String, Int> = Pair("",0)

    fun setSearchedTextAndCategory(searchedText : String, category: Int){
        searchedTextAndCategory = Pair(searchedText, category)
    }

    fun updateCategory(category: Int){
        searchedTextAndCategory = Pair(searchedTextAndCategory.first, category)
    }

    fun getSearchedTextAndCategory() : Pair<String, Int> {
        return searchedTextAndCategory
    }

    fun getCategoryName() : String {
        return when(searchedTextAndCategory.second){
            0 -> iTunesSearchKeys.MOVIES
            1 -> iTunesSearchKeys.MUSIC
            2 -> iTunesSearchKeys.BOOKS
            3 -> iTunesSearchKeys.PODCAST
            else -> iTunesSearchKeys.MOVIES
        }
    }

    fun getBooks() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiRepository.getItemList(searchedTextAndCategory.first,20, iTunesSearchKeys.BOOKS)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getMovies() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiRepository.getItemList(searchedTextAndCategory.first,20, iTunesSearchKeys.MOVIES)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getPodcasts() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiRepository.getItemList(searchedTextAndCategory.first,20, iTunesSearchKeys.PODCAST)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getMusics() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiRepository.getItemList(searchedTextAndCategory.first,20, iTunesSearchKeys.MUSIC)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}