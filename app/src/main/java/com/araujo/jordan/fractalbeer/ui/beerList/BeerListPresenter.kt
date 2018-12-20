package com.araujo.jordan.fractalbeer.ui.beerList

import com.araujo.jordan.fractalbeer.api.BeerInteractor
import com.araujo.jordan.fractalbeer.model.Beer


class BeerListPresenter : BeerListContract.Presenter {


    private lateinit var view: BeerListContract.View
    private lateinit var beerInteractor: BeerInteractor

    override fun loadData(page: Int) {
        if (page == 1)
            view.showLoading(true)
        beerInteractor.orderBeers(page)
    }

    override fun attach(view: BeerListContract.View) {
        this.view = view
    }

    override fun resume() {
        beerInteractor = BeerInteractor(this)
    }

    fun loadDataComplete(list: List<Beer>) {
        view.showLoading(false)
        view.loadDataSuccess(list)
    }

    override fun queryData(data: String) {
        beerInteractor.orderBeersByName(data)
//        view.showLoading(true)
    }

    fun queryDataComplete(list: List<Beer>) {
        view.showLoading(false)
        view.loadQuerySuccess(list)
    }
}