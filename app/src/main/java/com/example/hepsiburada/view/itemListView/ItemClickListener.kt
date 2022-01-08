package com.example.hepsiburada.view.itemListView

import com.example.hepsiburada.network.response.Result

interface ItemClickListener {
    fun onRecyclerViewItemClick(clickedItemDetails : Result)
}