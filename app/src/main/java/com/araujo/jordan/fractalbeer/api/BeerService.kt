package com.araujo.jordan.fractalbeer.api


import com.araujo.jordan.fractalbeer.model.Beer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * BeerService
 * Implements the requests itself
 * Created by araujojordan on 18/12/18.
 */

interface BeerService {

    @GET("beers?")
    fun getBeers(
        @Query("page") page: String
    ): Call<List<Beer>>


    @GET("beers?")
    fun getBeersByName(
        @Query("beer_name") page: String
    ): Call<List<Beer>>

}