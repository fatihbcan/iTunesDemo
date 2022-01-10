package com.example.hepsiburada.view.itemListView

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hepsiburada.viewModels.ItemListViewModel
import com.example.hepsiburada.R
import com.example.hepsiburada.databinding.ItemListFragmentBinding
import com.example.hepsiburada.network.response.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_list_fragment.*

@AndroidEntryPoint
class ItemListFragment : Fragment(R.layout.item_list_fragment) {

    private var _binding : ItemListFragmentBinding? = null
    private val binding get() = _binding!!


    private val viewModel by viewModels<ItemListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = ItemListFragmentBinding.bind(view)

        val adapter = ItemListPagingAdapter()

        binding.customSearchBar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchPhotos(binding.customSearchBar.getSearchedText(),binding.categories.position)
                binding.recyclerView.scrollToPosition(0)
                true
            } else {
                false
            }
        }

        binding.categories.setOnPositionChangedListener { position ->
            viewModel.searchPhotos(binding.customSearchBar.getSearchedText(), position)
        }

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ItemListLoadStateAdapter { adapter.retry() },
                footer = ItemListLoadStateAdapter { adapter.retry() }
            )
        }
        viewModel.listItems.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}