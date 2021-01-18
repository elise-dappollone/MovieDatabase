package com.edapp.moviedatabase.views

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.edapp.moviedatabase.views.ItemDetailFragment.Companion.ITEM_DETAIL
import com.edapp.moviedatabase.R
import com.edapp.moviedatabase.models.Movie

class ItemDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val movie = intent.extras?.get(ITEM_DETAIL) as Movie

            val fragment = ItemDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ITEM_DETAIL, movie)
                }
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo(Intent(this, ItemListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}