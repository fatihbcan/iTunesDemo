package com.example.hepsiburada.view.itemListView

import com.example.hepsiburada.R
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.hepsiburada.databinding.RecyclerViewItemBinding
import com.example.hepsiburada.network.response.Result
import com.example.hepsiburada.network.response.ResultList


class ItemListAdapter(private var listItems: ResultList,
                      private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemListViewHolder(
            DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.recycler_view_item,
                    parent,
                    false
            ), itemClickListener
    )

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        holder.bind(listItems.resultList[position])
    }

    override fun getItemCount(): Int = listItems.resultList.size

    fun addListItems(items: ResultList) {
        this.listItems = items
    }

    class ItemListViewHolder(
            private val recyclerViewItemBinding: RecyclerViewItemBinding,
            private val itemClickListener: ItemClickListener)
        : ViewHolder(recyclerViewItemBinding.root){

        fun bind(item: Result){
            Log.d("Fatih", "binded item : " + item.trackName)
            recyclerViewItemBinding.itemClickListener = itemClickListener
        }
    }
}