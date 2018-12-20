package com.araujo.jordan.fractalbeer.ui

/**
 * BasePresenter
 * Abstract Presenter that would be implement in many Presenters if the app scale more
 * Created by araujojordan on 18/12/18.
 */

interface BasePresenter<in T> {
    fun attach(view: T)
    fun resume()

}

interface BaseView

