package com.araujo.jordan.fractalbeer.ui.beerList

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.araujo.jordan.fractalbeer.R
import com.araujo.jordan.fractalbeer.model.Beer
import com.araujo.jordan.fractalbeer.ui.beerDetail.BeerDetailsActivity
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import kotlinx.android.synthetic.main.activity_beerlist.*


/**
 * BeerListActivity
 * Start screen from application, it shows a list of beers from PunkAPI
 * Created by araujojordan on 19/12/2018
 */
class BeerListActivity : AppCompatActivity(), BeerListContract.View {

    private var presenter = BeerListPresenter() //from MVP
    private var beerAdapter = BeerAdapter()

    private var page = 0 //for pagination
    private var skeletonScreen: RecyclerViewSkeletonScreen? = null //fancy loading

    //Infinite scroll implementation
    var listener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (recyclerView.canScrollVertically(1).not()) {
                presenter.loadData(++page)
            }
        }
    }

    //Callback from the click of the recycleview item
    //It comes with the Beer object and the view of click
    var listCallback = object : BeerAdapter.ListCallback {
        override fun onRetrieveBeer(beer: Beer, view: View) {
            val intent = Intent(
                this@BeerListActivity,
                BeerDetailsActivity::class.java
            )

            val p1 = Pair.create(view.findViewById(R.id.beeritem_img) as View, "beerImg")
            val options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this@BeerListActivity, p1)
            intent.putExtra(BeerDetailsActivity.EXTRA_BEER, beer)
            startActivity(intent, options.toBundle())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beerlist)

        setSupportActionBar(beerlist_toolbar)
        beerlist_recyleview.adapter = beerAdapter
        beerAdapter.attachCallback(listCallback)

        presenter.attach(this)
        presenter.resume()
        showLoading(true)
        presenter.loadData(++page)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.beerlist_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchManager = this@BeerListActivity.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        var searchView: SearchView? = MenuItemCompat.getActionProvider(searchItem) as SearchView?
        if (searchItem != null)
            searchView = searchItem.actionView as SearchView
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(this@BeerListActivity.componentName))
        searchView?.queryHint = "Buscar Cerveja..."
        searchView?.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    query(newText)
                    return false
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    query(query)
                    return true
                }
            }
        )

        searchItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {

//                beerlist_toolbar.background = ColorDrawable(Color.WHITE)
//                (searchView?.findViewById(R.id.search_src_text) as EditText).setTextColor(Color.BLACK)
//                (searchView.findViewById(R.id.search_src_text) as EditText).setHintTextColor(Color.BLACK)
//                (searchView.findViewById(R.id.search_button) as ImageView).setColorFilter(Color.BLACK)
//                (searchView.findViewById(R.id.search_mag_icon) as ImageView).setColorFilter(Color.BLACK)
//                (searchView.findViewById(R.id.search_close_btn) as ImageView).setColorFilter(Color.BLACK)


                return true
            }

            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                beerlist_toolbar.background = ColorDrawable(
                    ActivityCompat
                        .getColor(this@BeerListActivity, R.color.colorPrimary)
                )
                beerlist_toolbar.setTitleTextColor(Color.WHITE)
                showEmptyListImage(false)
                showLoading(true)
                page = 1
                beerAdapter.clear()
                presenter.loadData(page)
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }


    // Query the data from PunkAPI, using the Presenter and Interactor as interceptor
    fun query(newText: String) {
        if (newText.isBlank()) {
            Log.d("onQueryTextSubmit", "isBlank")
            showEmptyListImage(false)
            showLoading(true)
            page = 1
            beerAdapter.clear()
            presenter.loadData(page)
        } else {
            showEmptyListImage(false)
            showLoading(true)
            presenter.queryData(newText)
        }
    }

    // Show the fancy skeleton cards
    override fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            skeletonScreen = Skeleton.bind(beerlist_recyleview)
                .adapter(beerAdapter)
                .load(R.layout.listitem_beerskeleton)
                .color(R.color.colorSecondary)
                .shimmer(true)
                .show()
        } else {
            skeletonScreen?.hide()
        }
    }

    override fun showErrorMessage(error: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.thereWasAnError))
            .setMessage(error)
            .setCancelable(true).create().show()
    }

    //Callback from Presenter, returns a list of beers WITH query
    override fun loadQuerySuccess(list: List<Beer>) {
        runOnUiThread {
            page = 0
            beerAdapter.clear()
            beerAdapter.addToList(list as MutableList<Beer>)
            beerlist_recyleview.removeOnScrollListener(listener)
            showEmptyListImage(list.isEmpty())
        }
    }

    //Callback from Presenter, returns a list of beers WITHOUT query
    override fun loadDataSuccess(list: List<Beer>) {
        runOnUiThread {
            beerAdapter.addToList(list as MutableList<Beer>)
            beerlist_recyleview.addOnScrollListener(listener)
        }
    }

    //When the query comes empty, show the image of nothing was found
    fun showEmptyListImage(isEmpty: Boolean) {
        if (isEmpty) {
            beerlist_empty.visibility = View.VISIBLE
            beerlist_recyleview.visibility = View.GONE
        } else {
            beerlist_empty.visibility = View.GONE
            beerlist_recyleview.visibility = View.VISIBLE
        }
    }
}
