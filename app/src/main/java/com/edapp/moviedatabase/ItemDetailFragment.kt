package com.edapp.moviedatabase

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.edapp.moviedatabase.api.MovieApiService
import com.edapp.moviedatabase.api.MovieRepository
import com.edapp.moviedatabase.dummy.DummyContent
import com.edapp.moviedatabase.models.Movie
import com.squareup.picasso.Picasso

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : Fragment() {

    lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ITEM_DETAIL)) {
                movie = it.get(ITEM_DETAIL) as Movie
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title =
            movie?.title
        
        Picasso.with(this.requireContext())
            .load(SimpleItemRecyclerViewAdapter.BASE_URL + IMAGE_SIZE + movie.backdrop_path)
            .into(activity?.findViewById<ImageView>(R.id.backdrop))

        movie?.let {
            rootView.findViewById<TextView>(R.id.item_detail_title).text = it.overview
        }

        return rootView
    }

    companion object {
        const val ITEM_DETAIL = "item_detail"
        const val IMAGE_SIZE = "w500"
    }
}