package com.example.hepsiburada.widgets

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.hepsiburada.R

@BindingAdapter("imageUrl")
fun setImageView(view: ImageView, uri: String) {
    val options = RequestOptions()
        .error(R.drawable.search_icon)
    Glide.with(view.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(view)
}