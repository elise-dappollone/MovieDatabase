package com.edapp.moviedatabase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edapp.moviedatabase.presenters.ItemListViewModel
import com.edapp.moviedatabase.presenters.ItemListViewModelFactory
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton


class ItemListActivity : AppCompatActivity() {

    lateinit var viewModel: ItemListViewModel
    lateinit var viewModelFactory: ItemListViewModelFactory
    lateinit var adapter: SimpleItemRecyclerViewAdapter
    lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager

    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        viewModelFactory = ItemListViewModelFactory()

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ItemListViewModel::class.java)

        recyclerView = findViewById(R.id.item_list)
        layoutManager = recyclerView.layoutManager ?: LinearLayoutManager(this)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        findViewById<ExtendedFloatingActionButton>(R.id.fab).setOnClickListener { view ->
            updateRecyclerView()
        }

        if (findViewById<NestedScrollView>(R.id.item_detail_container) != null) {
            twoPane = true
        }
        viewModel.onCreate()
        setupRecyclerView(recyclerView)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter =  SimpleItemRecyclerViewAdapter(this, viewModel.movieList, twoPane)
        recyclerView.adapter = adapter
    }

    private fun updateRecyclerView() {
        viewModel.loadMoreClicked()
        adapter.updateData(viewModel.movieList)
    }
}