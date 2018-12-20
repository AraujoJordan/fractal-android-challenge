package com.araujo.jordan.fractalbeer.ui

/**
 * Created by araujojordan on 18/12/18.
 */


interface BasePresenter<in T> {
    fun attach(view: T)
    fun resume()

}

interface BaseView {

}

