package com.araujo.jordan.fractalbeer.ui

import android.content.Context
import android.content.Intent
import com.araujo.jordan.fractalbeer.model.Beer
import com.araujo.jordan.fractalbeer.ui.beerDetail.BeerDetailsActivity
import com.araujo.jordan.fractalbeer.ui.beerList.BeerListActivity

class NavigationCommand {

    fun navigateBeerDetails(context: Context, beer: Beer) {
        val intent = Intent(
            context,
            BeerDetailsActivity::class.java
        )
        intent.putExtra(BeerDetailsActivity.EXTRA_BEER, beer)
        context.startActivity(intent)
    }

    fun navigateBeerList() {
        fun navigateBeerDetails(context: Context) {
            context.startActivity(
                Intent(
                    context,
                    BeerListActivity::class.java
                )
            )
        }
    }
}