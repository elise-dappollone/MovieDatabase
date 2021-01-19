package com.edapp.moviedatabase.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edapp.moviedatabase.R
import com.edapp.moviedatabase.SimpleItemRecyclerViewAdapter
import com.edapp.moviedatabase.viewmodels.ItemListViewModel
import com.edapp.moviedatabase.viewmodels.ItemListViewModelFactory
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.Dispatchers


class ItemListActivity : AppCompatActivity() {

    private lateinit var viewModel: ItemListViewModel
    private lateinit var viewModelFactory: ItemListViewModelFactory
    private lateinit var adapter: SimpleItemRecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager

    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        viewModelFactory = ItemListViewModelFactory(Dispatchers.Default)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ItemListViewModel::class.java)

        recyclerView = findViewById(R.id.item_list)
        layoutManager = recyclerView.layoutManager ?: LinearLayoutManager(this)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        findViewById<ExtendedFloatingActionButton>(R.id.fab).setOnClickListener {
            updateRecyclerView()
        }

        if (findViewById<NestedScrollView>(R.id.item_detail_container) != null) {
            twoPane = true
        }
        viewModel.onCreate()
        setupRecyclerView(recyclerView)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = SimpleItemRecyclerViewAdapter(
            this,
            viewModel.movieList,
            twoPane
        )
        recyclerView.adapter = adapter
    }

    private fun updateRecyclerView() {
        viewModel.loadMoreClicked()
        adapter.updateData(viewModel.movieList)
    }
}