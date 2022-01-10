package com.example.hepsiburada.view.itemListView

import com.example.hepsiburada.R
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.hepsiburada.databinding.RecyclerViewItemBinding
import com.example.hepsiburada.network.response.Result
import com.example.hepsiburada.network.response.ResultList


class ItemListAdapter(private var listItems: ResultList) : RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemListViewHolder(
            DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.recycler_view_item,
                    parent,
                    false
            )
    )

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        holder.bind(listItems.resultList[position])
    }

    override fun getItemCount(): Int = listItems.resultList.size

    fun addListItems(items: ResultList) {
        this.listItems = items
    }

    class ItemListViewHolder(
            private val recyclerViewItemBinding: RecyclerViewItemBinding)
        : ViewHolder(recyclerViewItemBinding.root){

        fun bind(item: Result){
           // recyclerViewItemBinding.item = item
        }
    }

}