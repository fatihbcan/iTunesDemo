package com.example.hepsiburada.view.itemListView

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hepsiburada.viewModels.ItemListViewModel
import com.example.hepsiburada.R
import com.example.hepsiburada.data.ItemListData
import com.example.hepsiburada.databinding.ItemListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemListFragment : Fragment(R.layout.item_list_fragment) , ItemListPagingAdapter.OnItemClickListener{

    private var _binding : ItemListFragmentBinding? = null
    private val binding get() = _binding!!


    private val viewModel by viewModels<ItemListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backGroundColor()
        _binding = ItemListFragmentBinding.bind(view)

        val adapter = ItemListPagingAdapter(this)

        binding.customSearchBar.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                if(text?.trim()?.length!! >= 2){ // trim used to get rid of spaces on start and end
                    viewModel.searchItems(text.trim().toString(),binding.categories.position) // changes live query data to perform api call
                    binding.recyclerView.scrollToPosition(0) // scrolls to top when new api call performed
                } else {
                    viewModel.searchItems("",binding.categories.position)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.categories.setOnPositionChangedListener { position ->
            viewModel.searchItems(binding.customSearchBar.getSearchedText(), position)
            binding.recyclerView.scrollToPosition(0)
        }

        //sets recycleView
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ItemListLoadStateAdapter { adapter.retry() },
                footer = ItemListLoadStateAdapter { adapter.retry() }
            )
        }

        //observes paging data and submit it to paging adapter
        viewModel.listItems.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(item: ItemListData) {
        val action = ItemListFragmentDirections.goToDetailPage(item, viewModel.getCurrentQuery())
        findNavController().navigate(action)
    }

    //handles all fragment color including status bar and navigation bar
    private fun backGroundColor() {
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), android.R.color.transparent)
        activity?.window?.navigationBarColor = ContextCompat.getColor(requireContext(), android.R.color.transparent)
        activity?.window?.setBackgroundDrawableResource(R.drawable.amber200_to_orange400_gradient)
    }
}