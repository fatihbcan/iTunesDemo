package com.example.hepsiburada.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hepsiburada.network.request.iTunesSearchApiHelper
import com.example.hepsiburada.repository.ApiRepository

class ViewModelFactory(private val apiHelper: iTunesSearchApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemListViewModel::class.java)) {
            return ItemListViewModel(ApiRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}