package com.edapp.moviedatabase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.edapp.moviedatabase.models.Movie
import com.edapp.moviedatabase.models.MovieDetail
import com.edapp.moviedatabase.presenters.ItemDetailPresenter
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*


class ItemDetailFragment : Fragment() {

    lateinit var movie: Movie
    lateinit var movieDetails: MovieDetail
    private val presenter = ItemDetailPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ITEM_DETAIL)) {
                movie = it.get(ITEM_DETAIL) as Movie
                movieDetails = presenter.onCreate(movie.id.toInt())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title =
            movie.title

        Picasso.with(this.requireContext())
            .load(SimpleItemRecyclerViewAdapter.BASE_URL + IMAGE_SIZE + movie.backdrop_path)
            .into(activity?.findViewById<ImageView>(R.id.backdrop))

        movie.let {
            rootView.findViewById<TextView>(R.id.overview).text = it.overview
            rootView.findViewById<TextView>(R.id.release_header).text = getString(R.string.release_date_header)
            rootView.findViewById<TextView>(R.id.release_detail).text = it.release_date
        }
        movieDetails.let {
            val numberFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US).apply {
                maximumFractionDigits = 0
            }
            val revenueString: String = numberFormat.format(it.revenue)
            val budgetString = numberFormat.format(it.budget)

            rootView.findViewById<TextView>(R.id.revenue_header).text = getString(R.string.revenue_header)
            rootView.findViewById<TextView>(R.id.revenue_detail).text = revenueString

            rootView.findViewById<TextView>(R.id.budget_header).text = getString(R.string.budget_header)
            rootView.findViewById<TextView>(R.id.budget_detail).text = budgetString

            rootView.findViewById<TextView>(R.id.runtime_header).text = getString(R.string.runtime_header)
            rootView.findViewById<TextView>(R.id.runtime_detail).text = it.runtime.toString() + " minutes"
        }

        return rootView
    }

    companion object {
        const val ITEM_DETAIL = "item_detail"
        const val ITEM_ID = "item_id"
        const val IMAGE_SIZE = "w500"
    }
}