package com.example.hepsiburada.view.itemListView

import com.example.hepsiburada.R
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.hepsiburada.network.response.Result
import com.example.hepsiburada.network.response.ResultList


class ItemListAdapter(private var listItems: ResultList) : RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return ItemListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        holder.bind(listItems.resultList[position])
    }

    override fun getItemCount(): Int = listItems.resultList.size

    fun addListItems(items: ResultList) {
        this.listItems = items
    }

    class ItemListViewHolder(itemView: View) : ViewHolder(itemView){

        fun bind(item: Result){
            Log.d("Fatih", "binded item : " + item.trackName)
        }
    }
}