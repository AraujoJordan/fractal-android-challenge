package com.araujo.jordan.fractalbeer.ui.beerList

import com.araujo.jordan.fractalbeer.ui.BasePresenter
import com.araujo.jordan.fractalbeer.ui.BaseView
import com.araujo.jordan.fractalbeer.model.Beer

/**
 * Manage the control or ViewProjection layers
 * Created by araujojordan on 18/12/2018.
 */

class BeerListContract {

    interface View: BaseView  {
        fun showLoading(isLoading: Boolean)
        fun showErrorMessage(error: String)
        fun loadDataSuccess(list: List<Beer>)
        fun loadQuerySuccess(list: List<Beer>)
    }

    interface Presenter:BasePresenter<View>{
        fun loadData(page:Int)
        fun queryData(data:String)
    }
}