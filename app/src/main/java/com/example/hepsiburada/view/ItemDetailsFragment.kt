package com.example.hepsiburada.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.hepsiburada.viewModels.ItemDetailsViewModel
import com.example.hepsiburada.R
import com.example.hepsiburada.databinding.ItemDetailsFragmentBinding
import com.example.hepsiburada.network.request.iTunesSearchKeys

class ItemDetailsFragment : Fragment() {

    private val args: ItemDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val viewWithBinding: ItemDetailsFragmentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_details_fragment,
            container,
            false
        )

        if(args.itemCategory == iTunesSearchKeys.BOOKS){
            if(!args.selectedItem.genreList.isNullOrEmpty()){
                args.selectedItem.genreName = args.selectedItem.genreList!![0]
            }
        }
        viewWithBinding.item = args.selectedItem
        viewWithBinding.category = args.itemCategory

        return viewWithBinding.root
    }


}