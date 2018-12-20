package com.araujo.jordan.fractalbeer.ui.beerList

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import com.araujo.jordan.fractalbeer.R
import com.araujo.jordan.fractalbeer.model.Beer
import com.araujo.jordan.fractalbeer.ui.beerDetail.BeerDetailsActivity
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import kotlinx.android.synthetic.main.activity_beerlist.*
import android.support.v4.view.MenuItemCompat.setOnActionExpandListener
import android.util.Log
import android.view.MenuItem


class BeerListActivity : AppCompatActivity(), BeerListContract.View {

    var presenter = BeerListPresenter()
    var beerAdapter = BeerAdapter()
    var page = 0
    private var skeletonScreen: RecyclerViewSkeletonScreen? = null

    var listener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (recyclerView.canScrollVertically(1).not()) {
                presenter.loadData(++page)
            }
        }
    }
    var listCallback = object : BeerAdapter.ListCallback {
        override fun OnRetrieveBeer(beer: Beer, view: View) {
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
        searchView?.isIconified = false
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
                return true
            }
            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                Log.d("onMenuItActionCollapse",menuItem.toString())
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

    fun query(newText:String) {
        if (newText.isBlank()) {
            Log.d("onQueryTextSubmit","isBlank")
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

    override fun loadQuerySuccess(list: List<Beer>) {
        runOnUiThread {
            page = 0
            beerAdapter.clear()
            beerAdapter.addToList(list as MutableList<Beer>)
            beerlist_recyleview.removeOnScrollListener(listener)
            showEmptyListImage(list.isEmpty())
        }
    }

    override fun loadDataSuccess(list: List<Beer>) {
        runOnUiThread {
            beerAdapter.addToList(list as MutableList<Beer>)
            beerlist_recyleview.addOnScrollListener(listener)
        }
    }

    fun showEmptyListImage(isEmpty:Boolean) {
        if(isEmpty) {
            beerlist_empty.visibility = View.VISIBLE
            beerlist_recyleview.visibility = View.GONE
        } else {
            beerlist_empty.visibility = View.GONE
            beerlist_recyleview.visibility = View.VISIBLE
        }
    }
}
