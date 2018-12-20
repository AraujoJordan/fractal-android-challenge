package com.araujo.jordan.fractalbeer

import android.support.test.InstrumentationRegistry
import android.support.test.InstrumentationRegistry.getContext
import android.support.test.filters.SmallTest
import android.support.test.runner.AndroidJUnit4
import com.araujo.jordan.fractalbeer.model.Beer
import com.araujo.jordan.fractalbeer.ui.beerList.BeerListActivity
import com.araujo.jordan.fractalbeer.ui.beerList.BeerListContract
import com.araujo.jordan.fractalbeer.ui.beerList.BeerListPresenter

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class JobListTest  {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()



        assertEquals("com.araujo.jordan.fractalbeer", appContext.packageName)
    }

    @SmallTest
    fun getBeer() {
        val presenter = BeerListPresenter()
        presenter.attach(object : BeerListContract.View {
            override fun showLoading(isLoading: Boolean) {
                System.out.println("showLoading() " + isLoading.toString())
            }

            override fun showErrorMessage(error: String) {
                System.out.println("showErrorMessage() $error")
            }

            override fun loadDataSuccess(list: List<Beer>) {
                System.out.println("loadDataSuccess() ${list.toString()}")
                assertEquals(list.size, 25)
            }

            override fun loadQuerySuccess(list: List<Beer>) {
                System.out.println("loadQuerySuccess() $list.size.toString()")
            }
        })
        presenter.resume()
        presenter.loadData(1)
        assertEquals(true, true)
    }


}
