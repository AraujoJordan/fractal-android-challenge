package com.araujo.jordan.fractalbeer.ui.beerDetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.araujo.jordan.fractalbeer.R
import com.araujo.jordan.fractalbeer.model.Beer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_beerdetails.*

/**
 * BeerDetailsActivity
 * Detail screen, it shows a detailed screen of one beer
 * Created by araujojordan on 19/12/2018
 */
class BeerDetailsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_BEER = "EXTRA_BEER"
    }

    private var beer: Beer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beerdetails)

        setSupportActionBar(beerdetail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        beer = intent.getParcelableExtra(BeerDetailsActivity.EXTRA_BEER) as? Beer

        beerdetail_title.text = beer?.name
        beerdetail_tagline.text = beer?.tagline
        beerdetail_desc.text = beer?.description
        if (!beer?.image_url.isNullOrEmpty())
            Picasso.get()
                .load(beer?.image_url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(beerdetail_image)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
