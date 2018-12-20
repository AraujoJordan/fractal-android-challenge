package com.araujo.jordan.fractalbeer.api

import android.util.Log
import com.araujo.jordan.fractalbeer.model.Beer
import com.araujo.jordan.fractalbeer.ui.beerList.BeerListPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * BeerInteractor
 * API request manager that resolves the Retrofit2 usage
 * Created by araujojordan on 18/12/18.
 */
class BeerInteractor {

    private val BASE_URL = "https://api.punkapi.com/v2/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val beerAPI = retrofit.create(BeerService::class.java)
    private var presenter: BeerListPresenter? = null

    // Attach presenter to use its methods of callback
    fun attachPresenter(presenter: BeerListPresenter) {
        this.presenter = presenter
    }

     // Request 25 beers from the PunkAPI with pagination
    fun orderBeers(page: Int) {
        beerAPI.getBeers(page.toString()).enqueue(object : Callback<List<Beer>> {
            override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                Log.e("BeerInteractor", "OrderBeers() onFailure()" + t.message)
            }

            override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                if (response.isSuccessful) {
                    val beerList = response.body()
                    presenter?.loadDataComplete(beerList!!)
                } else {
                    Log.e("BeerInteractor", "OrderBeers() onResponse()" + response.errorBody())
                    presenter?.loadDataComplete(ArrayList())
                }
            }
        })
    }


    // Request beers from the PunkAPI by query the beer name
    fun orderBeersByName(beerName: String) {
        beerAPI.getBeersByName(beerName).enqueue(object : Callback<List<Beer>> {
            override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                Log.e("BeerInteractor", "OrderBeers() onFailure()" + t.message)
            }

            override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                if (response.isSuccessful) {
                    val beerList = response.body()
                    presenter?.queryDataComplete(beerList!!)
                } else {
                    Log.e("BeerInteractor", "OrderBeers() onResponse()" + response.errorBody())
                    presenter?.queryDataComplete(ArrayList())
                }
            }

        })
    }
}