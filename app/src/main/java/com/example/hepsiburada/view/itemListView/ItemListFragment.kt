package com.example.hepsiburada.view.itemListView

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hepsiburada.viewModels.ItemListViewModel
import com.example.hepsiburada.R
import com.example.hepsiburada.network.Status
import com.example.hepsiburada.network.request.iTunesSearchApiBuilder
import com.example.hepsiburada.network.request.iTunesSearchApiHelper
import com.example.hepsiburada.network.response.ResultList
import com.example.hepsiburada.viewModels.ViewModelFactory
import kotlinx.android.synthetic.main.item_list_fragment.*
import retrofit2.HttpException

class ItemListFragment : Fragment() {

    companion object {
        fun newInstance() = ItemListFragment()
    }

    private lateinit var viewModel: ItemListViewModel
    private lateinit var adapter: ItemListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_list_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        setupUI()
        setupObservers()

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this,  ViewModelFactory(iTunesSearchApiHelper(iTunesSearchApiBuilder.apiService)))[ItemListViewModel::class.java]
    }

    private fun setupObservers() {
        viewModel.getMovies().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Log.d("Fatih","success worked")
                        Log.d("Fatih","size : "+ resource.data!!.body())
                        resource.data.let { items -> items.body()?.let { it1 -> retrieveList(it1) } }
                    }
                    Status.ERROR -> {
                        Log.d("Fatih","error worked")
                        it.message?.let { it1 -> Log.d("Fatih", it1) }
                    }
                    Status.LOADING -> {
                        Log.d("Fatih","loading")
                    }
                }
            }
        })
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ItemListAdapter(ResultList(ArrayList()))
        recyclerView.addItemDecoration(
                DividerItemDecoration(
                        recyclerView.context,
                        (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
        )
        recyclerView.adapter = adapter
    }

    private fun retrieveList(items: ResultList) {
        adapter.apply {
            addListItems(items)
            notifyDataSetChanged()
        }
    }

}