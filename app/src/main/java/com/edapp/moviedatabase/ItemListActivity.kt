package com.edapp.moviedatabase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.edapp.moviedatabase.models.Movie
import com.edapp.moviedatabase.presenters.ItemListPresenter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton


/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */

class ItemListActivity : AppCompatActivity() {

    private val presenter = ItemListPresenter()
    private lateinit var popularMovies: Array<Movie>
    lateinit var adapter: SimpleItemRecyclerViewAdapter

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        val recyclerView = findViewById<RecyclerView>(R.id.item_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        findViewById<ExtendedFloatingActionButton>(R.id.fab).setOnClickListener { view ->
            popularMovies = presenter.loadMoreClicked()
            updateRecyclerView(popularMovies)
        }

        if (findViewById<NestedScrollView>(R.id.item_detail_container) != null) {
            twoPane = true
        }

        popularMovies = presenter.onCreate()

        setupRecyclerView(recyclerView)
    }


    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter =  SimpleItemRecyclerViewAdapter(this, popularMovies, twoPane)
        recyclerView.adapter = adapter
    }

    private fun updateRecyclerView(data: Array<Movie>) {
        adapter.updateData(data)

    }
}