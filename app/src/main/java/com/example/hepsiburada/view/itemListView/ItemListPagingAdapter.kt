package com.example.hepsiburada.view.itemListView

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.hepsiburada.R
import com.example.hepsiburada.data.ItemListData
import com.example.hepsiburada.databinding.RecyclerViewItemBinding

class ItemListPagingAdapter :
    PagingDataAdapter<ItemListData, ItemListPagingAdapter.ItemListPagingViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListPagingViewHolder {
        val binding =
            RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return ItemListPagingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemListPagingViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class ItemListPagingViewHolder (private val binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemListData) {
            Log.d("Fatih","item name :"+item.trackName)
            binding.item = item
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ItemListData>() {
            override fun areItemsTheSame(oldItem: ItemListData, newItem: ItemListData) =
                oldItem.trackId == newItem.trackId

            override fun areContentsTheSame(oldItem: ItemListData, newItem: ItemListData) =
                oldItem == newItem
        }
    }
}