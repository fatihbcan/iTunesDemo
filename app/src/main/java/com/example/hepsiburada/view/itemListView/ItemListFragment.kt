package com.example.hepsiburada.view.itemListView

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup
import com.example.hepsiburada.viewModels.ItemListViewModel
import com.example.hepsiburada.R
import com.example.hepsiburada.network.Status
import com.example.hepsiburada.network.request.iTunesSearchApiBuilder
import com.example.hepsiburada.network.request.iTunesSearchApiHelper
import com.example.hepsiburada.network.response.Result
import com.example.hepsiburada.network.response.ResultList
import com.example.hepsiburada.viewModels.ViewModelFactory
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup.OnPositionChangedListener
import com.example.hepsiburada.widgets.CustomSearchBar
import kotlinx.android.synthetic.main.item_list_fragment.*


class ItemListFragment : Fragment(), ItemClickListener {

    companion object {
        fun newInstance() = ItemListFragment()
        val TAG = ItemListFragment::class.java.simpleName
    }

    private lateinit var viewModel: ItemListViewModel
    private lateinit var adapter: ItemListAdapter
    private lateinit var segmentedButtonGroup : SegmentedButtonGroup
    private lateinit var customSearchBar: CustomSearchBar
    private lateinit var loadingBar : ProgressBar
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.item_list_fragment, container, false)
        segmentedButtonGroup = view.findViewById(R.id.categories)
        customSearchBar = view.findViewById(R.id.customSearchBar)
        loadingBar = view.findViewById(R.id.loading_spinner)
        recyclerView = view.findViewById(R.id.recyclerView)
        setupUI()
        setupSearchListener()
        setupSegmentedControlListener()
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this,  ViewModelFactory(iTunesSearchApiHelper(iTunesSearchApiBuilder.apiService)))[ItemListViewModel::class.java]
    }

    private fun setupSearchListener(){
        customSearchBar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.setSearchedTextAndCategory(customSearchBar.getSearchedText(), segmentedButtonGroup.position)
                loadData()
                true
            } else {
                false
            }
        }
    }

    private fun loadData(){
        when(viewModel.getSearchedTextAndCategory().second){
            0 -> getMovieList()
            1 -> getMusicList()
            2 -> getEBookList()
            3 -> getPodcastList()
        }
    }

    private fun getMovieList(){
        viewModel.getMovies().observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        loadingBar.visibility = View.GONE
                        resource.data.let { items -> items?.body()?.let { it1 -> retrieveList(it1) } }
                    }
                    Status.ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        loadingBar.visibility = View.GONE
                        it.message?.let { it1 -> Log.d(TAG, it1) }
                    }
                    Status.LOADING -> {
                        recyclerView.visibility = View.GONE
                        loadingBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun getMusicList(){
        viewModel.getMusics().observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        loadingBar.visibility = View.GONE
                        resource.data.let { items -> items?.body()?.let { it1 -> retrieveList(it1) } }
                    }
                    Status.ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        loadingBar.visibility = View.GONE
                        it.message?.let { it1 -> Log.d(TAG, it1) }
                    }
                    Status.LOADING -> {
                        recyclerView.visibility = View.GONE
                        loadingBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun getPodcastList(){
        viewModel.getPodcasts().observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        loadingBar.visibility = View.GONE
                        resource.data.let { items -> items?.body()?.let { it1 -> retrieveList(it1) } }
                    }
                    Status.ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        loadingBar.visibility = View.GONE
                        it.message?.let { it1 -> Log.d(TAG, it1) }
                    }
                    Status.LOADING -> {
                        recyclerView.visibility = View.GONE
                        loadingBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun getEBookList(){
        viewModel.getBooks().observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        loadingBar.visibility = View.GONE
                        resource.data.let { items -> items?.body()?.let { it1 -> retrieveList(it1) } }
                    }
                    Status.ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        loadingBar.visibility = View.GONE
                        it.message?.let { it1 -> Log.d(TAG, it1) }
                    }
                    Status.LOADING -> {
                        recyclerView.visibility = View.GONE
                        loadingBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setupSegmentedControlListener(){
        segmentedButtonGroup.onPositionChangedListener = OnPositionChangedListener {
                position ->
            viewModel.updateCategory(position)
            loadData()
        }
    }

    private fun setupUI() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.apply {
            layoutManager = gridLayoutManager
        }
        adapter = ItemListAdapter(ResultList(ArrayList()), this)
        recyclerView.adapter = adapter
    }

    private fun retrieveList(items: ResultList) {
        adapter.apply {
            addListItems(items)
            notifyDataSetChanged()
        }
    }

    // navigates to event list fragment for clicked city
    override fun onRecyclerViewItemClick(clickedItemDetails : Result) {
        val action = ItemListFragmentDirections.goToDetailPage()
        findNavController().navigate(action)
    }

}