package com.edapp.moviedatabase

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edapp.moviedatabase.dummy.DummyContent
import com.edapp.moviedatabase.models.Movie
import com.squareup.picasso.Picasso

class SimpleItemRecyclerViewAdapter(private val parentActivity: ItemListActivity,
                                    private var values: Array<Movie>,
                                    private val twoPane: Boolean) :
    RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as DummyContent.DummyItem
            if (twoPane) {
                val fragment = ItemDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ItemDetailFragment.ARG_ITEM_ID, item.id)
                    }
                }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id)
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        Picasso.with(holder.posterImage.context).load(ItemListActivity.BASE_URL + ItemListActivity.IMAGE_SIZE + item.poster_path).into(holder.posterImage)

        holder.title.text = item.title
        holder.overview.text = item.overview

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = values.size

    fun updateData(data: Array<Movie>) {
        values = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val posterImage: ImageView = view.findViewById(R.id.poster_image)
        val title: TextView = view.findViewById(R.id.title)
        val overview: TextView = view.findViewById(R.id.overview)
    }
}