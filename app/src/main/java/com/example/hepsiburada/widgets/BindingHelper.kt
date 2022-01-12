package com.example.hepsiburada.widgets

import android.annotation.SuppressLint
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.hepsiburada.R
import com.example.hepsiburada.network.request.iTunesSearchKeys
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

// binds image, if it fails (image url is empty for example) returns a red "X" as default
@BindingAdapter("imageUrl")
fun setImageView(view: ImageView, uri: String) {
    val options = RequestOptions()
        .error(R.drawable.image_load_failed_default)
    Glide.with(view.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(view)
}

// returns trackPrice or price ,which is differs for categories, and returns it with currency symbol
@SuppressLint("SetTextI18n")
@BindingAdapter(value = ["price", "trackPrice", "currencyCode"])
fun getPrice(view: TextView, price: Float, trackPrice: Float, currencyCode: String) {
    val currency = Currency.getInstance(currencyCode)

    if (trackPrice.toInt() == 0 && price.toInt() != 0) {
        view.text = "${currency.symbol} $price"
    } else if (trackPrice.toInt() != 0 && price.toInt() == 0) {
        view.text = "${currency.symbol} $trackPrice"
    } else {
        view.text = "Free"
    }
}


@SuppressLint("SetTextI18n")
@BindingAdapter(value = ["price", "rentalPrice", "trackPrice", "currencyCode"])
fun getPriceRentandTrack(
    view: TextView,
    price: Float,
    rentalPrice: Float,
    trackPrice: Float,
    currencyCode: String
) {
    // api returns "USD" may be on other cases "EURO" etc. this method parse it to symbol of that currency
    val currency = Currency.getInstance(currencyCode)

    /* api returns rental price, trackPrice and price differently for categories
    *  this method binds it according to provided data
    * */
    if (rentalPrice.toInt() == 0 && trackPrice.toInt() != 0 && price.toInt() == 0) {
        view.text = "Buy: ${currency.symbol}$trackPrice"
    } else if (rentalPrice.toInt() != 0 && trackPrice.toInt() != 0 && price.toInt() == 0) {
        view.text = "Rent: ${currency.symbol}$rentalPrice / Buy: ${currency.symbol}$trackPrice"
    } else if (rentalPrice.toInt() == 0 && trackPrice.toInt() == 0 && price.toInt() != 0) {
        view.text = "Buy: ${currency.symbol}$price"
    } else {
        view.text = "Free"
    }
}

// binds year for recyclerView
@BindingAdapter("setDate")
fun setDate(view: TextView, unformattedDate: String) {
    view.text = getDate(unformattedDate)
}

/*
*  to show genre time release date and category this method gets parameters
*  and returns according to category. Because books and podcasts doesn't have trackTime value
* */
@SuppressLint("SetTextI18n")
@BindingAdapter(value = ["genre", "time", "releaseDate", "category"])
fun bindGenreTimeReleaseDate(
    view: TextView,
    genre: String,
    time: Int,
    releaseDate: String,
    category: String
) {
    val trackTime = getTrackTime(time)
    val releaseDate = getDate(releaseDate)

    if (category == iTunesSearchKeys.BOOKS || category == iTunesSearchKeys.PODCAST) {
        view.text = "$genre \u2022 $releaseDate"
    } else {
        view.text = "$genre \u2022 $trackTime \u2022 $releaseDate"
    }
}

/* movie returns  longDescription, books returns description parameters from api
*  to handle this give them both together and return what you need
* */
@BindingAdapter(value = ["longDescription", "description"])
fun getDescription(view: TextView, longDescription: String, description: String) {
    if (longDescription.isNullOrEmpty()) {
        view.text = description
    } else {
        view.text = longDescription
    }
}

// parses date from api to get only year, if you need full date change date variable, if you want to edit text, edit return value
@SuppressLint("SimpleDateFormat")
fun getDate(unformattedDate: String): String {
    try {
        val dateFormatter: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val d: Date? = dateFormatter.parse(unformattedDate)
        val date: DateFormat = SimpleDateFormat("yyyy")
        return date.format(d!!)
    } catch (e: Exception) {
        Log.d("BindingHelper", "setDate something went wrong !!")
    }

    return ""
}

/*
*  track time returns as a milliseconds from Api, I wanted to parse it differently for plural and singular
*  so this method parse milliseconds to text as a plural version or a singular version
* */
fun getTrackTime(milliseconds: Int): String {
    var parsedText = ""
    if (TimeUnit.MILLISECONDS.toHours(milliseconds.toLong()) > 1) { // if hour >1

        if (TimeUnit.MILLISECONDS.toMinutes(milliseconds.toLong()) > 1) { // if hour >1 and minutes >1
            parsedText = String.format(
                "%d Hours %d Minutes",
                TimeUnit.MILLISECONDS.toHours(milliseconds.toLong()),
                TimeUnit.MILLISECONDS.toMinutes(milliseconds.toLong()) - (60 * TimeUnit.MILLISECONDS.toHours(
                    milliseconds.toLong()
                ))
            )

        } else if (TimeUnit.MILLISECONDS.toMinutes(milliseconds.toLong()) < 1) { // if hour >1 and minutes == 0
            parsedText = String.format(
                "%d Hours",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds.toLong())
            )
        }

    } else if (TimeUnit.MILLISECONDS.toHours(milliseconds.toLong()) < 1) { // if hour == 0

        if (TimeUnit.MILLISECONDS.toMinutes(milliseconds.toLong()) > 1) { // if hour==0 and minutes >1
            parsedText = String.format(
                "%d Minutes %d Seconds",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds.toLong()) - (60 * TimeUnit.MILLISECONDS.toMinutes(
                    milliseconds.toLong()
                ))
            )


        } else if (TimeUnit.MILLISECONDS.toMinutes(milliseconds.toLong()) < 1) { // if hour==0 and minutes < 0
            parsedText = String.format(
                "%d Seconds",
                TimeUnit.MILLISECONDS.toSeconds(milliseconds.toLong())
            )
        }

    } else { // if hour == 1
        if (TimeUnit.MILLISECONDS.toMinutes(milliseconds.toLong()) > 1) { // if hour == 1 and minutes >1

            parsedText = String.format(
                "%d Hour %d Minutes",
                TimeUnit.MILLISECONDS.toHours(milliseconds.toLong()),
                TimeUnit.MILLISECONDS.toMinutes(milliseconds.toLong()) - (60 * TimeUnit.MILLISECONDS.toHours(
                    milliseconds.toLong()
                ))
            )

        } else if (TimeUnit.MILLISECONDS.toMinutes(milliseconds.toLong()) < 1) { // if hour  == 1 and minutes == 0

            parsedText = String.format(
                "%d Hour",
                TimeUnit.MILLISECONDS.toHours(milliseconds.toLong())
            )
        }
    }

    return parsedText
}