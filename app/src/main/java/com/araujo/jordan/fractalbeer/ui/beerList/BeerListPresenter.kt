package com.araujo.jordan.fractalbeer.ui.beerList

import com.araujo.jordan.fractalbeer.api.BeerInteractor
import com.araujo.jordan.fractalbeer.model.Beer

/**
 * BeerListPresenter Class
 * Uses Kotlin Data Class
 * SerializedName for the Retrofit speed inject the variables from API results
 * Parcelable for easily pass the Beer thought Activities
 * Created by araujojordan on 19/12/2018
 */
class BeerListPresenter : BeerListContract.Presenter {

    private lateinit var view: BeerListContract.View
    private lateinit var beerInteractor: BeerInteractor

    // Attach the View that will request usage from the Injection
    override fun attach(view: BeerListContract.View) {
        this.view = view
    }

    // Starts the Interactor
    override fun resume() {
        beerInteractor = BeerInteractor(this)
    }

    // Request to load beer list from BeerInteractor using pagination
    override fun loadData(page: Int) {
        if (page == 1)
            view.showLoading(true)
        beerInteractor.orderBeers(page)
    }


    // Callback from the Interactor of the orderBeers() request
    fun loadDataComplete(list: List<Beer>) {
        view.showLoading(false)
        view.loadDataSuccess(list)
    }

    // Request to load beer list using a query from BeerInteractor
    override fun queryData(data: String) {
        beerInteractor.orderBeersByName(data)
    }

    // Callback from the Interactor of the queryData() request
    fun queryDataComplete(list: List<Beer>) {
        view.showLoading(false)
        view.loadQuerySuccess(list)
    }
}